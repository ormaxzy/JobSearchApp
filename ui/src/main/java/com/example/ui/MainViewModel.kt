package com.example.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.JobRepository
import com.example.domain.models.Offer
import com.example.domain.models.Vacancy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: JobRepository
) : ViewModel() {

    private val _offers = MutableStateFlow<List<Offer>>(emptyList())
    val offers: StateFlow<List<Offer>> = _offers

    private val _vacancies = MutableStateFlow<List<Vacancy>>(emptyList())
    val vacancies: StateFlow<List<Vacancy>> = _vacancies

    private val _loading = MutableStateFlow(false)

    private val _error = MutableStateFlow<String?>(null)

    private val _favoriteVacancyCount = MutableLiveData<Int>()
    val favoriteVacancyCount: LiveData<Int> = _favoriteVacancyCount

    private val _favorites = MutableStateFlow<List<Vacancy>>(emptyList())
    val favorites = _favorites.asStateFlow()

    init {
        loadData()
        loadFavoriteCount()
    }

    fun loadData() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val (loadedOffers, loadedVacancies) = repository.getOffersAndVacancies()
                _offers.value = loadedOffers
                _vacancies.value = loadedVacancies
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            try {
                val favoriteVacancies = repository.getFavoriteVacancies()
                _favorites.value = favoriteVacancies
                _favoriteVacancyCount.value = favoriteVacancies.size
            } catch (e: Exception) {
                _favorites.value = emptyList()
                _favoriteVacancyCount.value = 0
            }
        }
    }

    fun addToFavorites(vacancy: Vacancy) {
        viewModelScope.launch {
            repository.addVacancyToFavorites(vacancy)
            refreshVacancies()
            loadFavorites()
        }
    }

    fun removeFromFavorites(vacancy: Vacancy) {
        viewModelScope.launch {
            repository.removeVacancyFromFavorites(vacancy)
            refreshVacancies()
            loadFavorites()
        }
    }

    private fun refreshVacancies() {
        viewModelScope.launch {
            try {
                val (_, loadedVacancies) = repository.getOffersAndVacancies()
                _vacancies.value = loadedVacancies
            } catch (_: Exception) {

            }
        }
    }

    private fun loadFavoriteCount() {
        viewModelScope.launch {
            try {
                val favorites = repository.getFavoriteVacancies()
                _favoriteVacancyCount.value = favorites.size
            } catch (e: Exception) {
                _favoriteVacancyCount.value = 0
            }
        }
    }

    fun updateFavoriteCount() {
        loadFavoriteCount()
    }


}
