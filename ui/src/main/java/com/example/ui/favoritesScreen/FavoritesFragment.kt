package com.example.ui.favoritesScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.Vacancy
import com.example.ui.MainViewModel
import com.example.ui.R
import com.example.ui.adapters.VacancyAdapter
import com.example.ui.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var vacancyAdapter: VacancyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        setupRecyclerView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFavorites()
    }

    private fun setupRecyclerView() {
        vacancyAdapter = VacancyAdapter(
            onFavoriteClick = { vacancy ->
                handleFavoriteClick(vacancy)
            },
            onItemClick = {
                val navController = findNavController()
                navController.navigate(R.id.action_favoritesFragment_to_vacancyDetailsFragment)
            },
            onShowMoreClick = { }
        )

        vacancyAdapter.updateShowMoreState(true)

        binding.rvFavorites.apply {
            adapter = vacancyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favorites.collect { favorites ->
                updateUI(favorites)
            }
        }
    }

    private fun updateUI(favorites: List<Vacancy>) {
        val totalCount = favorites.size

        val vacancyCountText = resources.getQuantityString(
            R.plurals.vacancy_count,
            totalCount,
            totalCount
        )
        binding.tvFavoritesCount.text = vacancyCountText

        vacancyAdapter.submitList(favorites)
    }

    private fun handleFavoriteClick(vacancy: Vacancy) {
        if (vacancy.isFavorite) {
            viewModel.removeFromFavorites(vacancy)
        } else {
            viewModel.addToFavorites(vacancy)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}