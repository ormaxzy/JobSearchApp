package com.example.domain

import com.example.domain.models.Offer
import com.example.domain.models.Vacancy

interface JobRepository {
    suspend fun getOffersAndVacancies(): Pair<List<Offer>, List<Vacancy>>
    suspend fun getFavoriteVacancies(): List<Vacancy>
    suspend fun addVacancyToFavorites(vacancy: Vacancy)
    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)
}