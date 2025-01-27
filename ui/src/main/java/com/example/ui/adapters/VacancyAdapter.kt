package com.example.ui.adapters

import android.annotation.SuppressLint
import com.example.domain.models.Vacancy
import com.example.ui.utils.ShowMoreItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class VacancyAdapter(
    onFavoriteClick: (Vacancy) -> Unit,
    onItemClick: (Vacancy) -> Unit,
    private val onShowMoreClick: () -> Unit
) : ListDelegationAdapter<List<Any>>() {

    private var isShowMoreClicked = false
    private var originalItems: List<Vacancy> = emptyList()

    init {
        delegatesManager.addDelegate(vacancyAdapterDelegate(onFavoriteClick, onItemClick))
        delegatesManager.addDelegate(showMoreAdapterDelegate {
            isShowMoreClicked = true
            updateDisplayedItems()
            onShowMoreClick()
        })
    }

    fun updateShowMoreState(showAll: Boolean) {
        isShowMoreClicked = showAll
        updateDisplayedItems()
    }

    fun submitList(vacancies: List<Vacancy>) {
        originalItems = vacancies
        updateDisplayedItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateDisplayedItems() {
        items = if (originalItems.size > 3 && !isShowMoreClicked) {
            originalItems.take(3) + ShowMoreItem(originalItems.size - 3)
        } else {
            originalItems
        }
        notifyDataSetChanged()
    }

    fun getCurrentVacancies(): List<Vacancy> {
        return items?.filterIsInstance<Vacancy>() ?: emptyList()
    }
}
