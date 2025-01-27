package com.example.ui.searchScreen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Vacancy
import com.example.ui.MainViewModel
import com.example.ui.R
import com.example.ui.adapters.RecommendationAdapter
import com.example.ui.adapters.VacancyAdapter
import com.example.ui.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var vacancyAdapter: VacancyAdapter

    private var isShowMoreClicked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)

        setupAdapters()
        setupListeners()
        observeViewModel()

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    private fun setupAdapters() {
        recommendationAdapter = RecommendationAdapter()

        vacancyAdapter = VacancyAdapter(
            onFavoriteClick = { vacancy ->
                handleFavoriteClick(vacancy)
            },
            onItemClick = {
                val navController = findNavController()
                navController.navigate(R.id.action_searchFragment_to_vacancyDetailsFragment)
            },
            onShowMoreClick = {
                isShowMoreClicked = true
                applyShowMoreUiState(true)
                vacancyAdapter.updateShowMoreState(true)
            }
        )

        binding.recommendationsList.apply {
            adapter = recommendationAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        binding.vacanciesList.apply {
            adapter = vacancyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    private fun setupListeners() {
        binding.leftIconButton.setOnClickListener {
            if (isShowMoreClicked) {
                isShowMoreClicked = false
                applyShowMoreUiState(false)
                vacancyAdapter.updateShowMoreState(false)
            } else {
                Log.d("SearchFragment", "Search icon pressed")
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.offers.collect { offers ->
                recommendationAdapter.submitList(offers)
                binding.recommendationsList.visibility =
                    if (offers.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.vacancies.collect { vacancies ->
                vacancyAdapter.submitList(vacancies)
            }
        }
    }

    private fun applyShowMoreUiState(isShowMore: Boolean) {
        if (isShowMore) {
            binding.leftIconButton.setImageResource(R.drawable.ic_arrow_back)

            binding.recommendationsList.visibility = View.GONE
            binding.vacanciesTitle.visibility = View.GONE
            binding.vacancyCountContainer.visibility = View.VISIBLE

            val totalCount = viewModel.vacancies.value.size
            val vacancyCountText = resources.getQuantityString(
                R.plurals.vacancy_count,
                totalCount,
                totalCount
            )

            binding.vacancyCountText.text = vacancyCountText

        } else {
            binding.leftIconButton.setImageResource(R.drawable.ic_search)

            binding.recommendationsList.visibility = View.VISIBLE
            binding.vacanciesTitle.visibility = View.VISIBLE

            binding.vacancyCountContainer.visibility = View.GONE
        }
    }
    private fun handleFavoriteClick(vacancy: Vacancy) {
        val toggledVacancy = vacancy.copy(isFavorite = !vacancy.isFavorite)

        val currentList = vacancyAdapter.getCurrentVacancies().toMutableList()
        val index = currentList.indexOfFirst { it.id == vacancy.id }
        if (index != -1) {
            currentList[index] = toggledVacancy
            vacancyAdapter.submitList(currentList)
        }

        if (toggledVacancy.isFavorite) {
            viewModel.addToFavorites(toggledVacancy)
        } else {
            viewModel.removeFromFavorites(toggledVacancy)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
