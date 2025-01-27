package com.example.ui.adapters

import android.view.View
import com.example.core.utils.DateUtils
import com.example.domain.models.Vacancy
import com.example.ui.R
import com.example.ui.databinding.ItemShowMoreBinding
import com.example.ui.databinding.ItemVacancyBinding
import com.example.ui.utils.ShowMoreItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding


fun vacancyAdapterDelegate(
    onFavoriteClick: (Vacancy) -> Unit,
    onItemClick: (Vacancy) -> Unit
) = adapterDelegateViewBinding<Vacancy, Any, ItemVacancyBinding>(
    { layoutInflater, root -> ItemVacancyBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        with(binding) {
            tvTitle.text = item.title
            tvCity.text = item.town
            tvCompany.text = item.company
            tvExperience.text = item.experience
            val formattedDate = DateUtils.formatToReadableDate(item.publishedDate)
            tvPublishedDate.text = context.getString(R.string.published_date, formattedDate)
            tvViewingNumber.text = if (item.lookingNumber != null) {
                context.resources.getQuantityString(
                    R.plurals.viewing_now,
                    item.lookingNumber ?: 0,
                    item.lookingNumber
                )
            } else {
                ""
            }
            if (item.salaryShort != null) {
                tvSalary.text = item.salaryShort
                tvSalary.visibility = View.VISIBLE
            }
            tvViewingNumber.visibility = if (item.lookingNumber != null) View.VISIBLE else View.GONE

            ivFavorite.setImageResource(
                if (item.isFavorite) R.drawable.ic_favorites_filled else R.drawable.ic_favorites
            )
            ivFavorite.setOnClickListener {
                onFavoriteClick(item)
            }

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}

fun showMoreAdapterDelegate(onShowMoreClick: () -> Unit) =
    adapterDelegateViewBinding<ShowMoreItem, Any, ItemShowMoreBinding>(
        { layoutInflater, root -> ItemShowMoreBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            val remain = item.remain

            val text = context.resources.getQuantityString(
                R.plurals.more_vacancies,
                remain,
                remain
            )
            binding.showMoreButton.text = text

            binding.showMoreButton.setOnClickListener {
                onShowMoreClick()
            }
        }
    }
