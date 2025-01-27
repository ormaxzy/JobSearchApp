package com.example.data

import com.example.data.api.JobSearchApi
import com.example.data.database.VacancyDao
import com.example.data.models.toDomain
import com.example.data.models.toEntity
import com.example.domain.JobRepository
import com.example.domain.models.Offer
import com.example.domain.models.Vacancy


class JobRepositoryImpl(
    private val api: JobSearchApi,
    private val vacancyDao: VacancyDao
) : JobRepository {

    override suspend fun getOffersAndVacancies(): Pair<List<Offer>, List<Vacancy>> {
        val response = api.getJobData()

        val favoriteFromServer = response.vacancies.filter { it.isFavorite }
        favoriteFromServer.forEach { vacancy ->
            vacancyDao.insertVacancy(vacancy.toEntity())
        }

        val localFavorites = vacancyDao.getFavoriteVacancies().map { it.toDomain() }

        val vacancies = response.vacancies.map { vacancyDto ->
            val isFavoriteLocally = localFavorites.any { it.id == vacancyDto.id }
            vacancyDto.toDomain().copy(isFavorite = isFavoriteLocally || vacancyDto.isFavorite)
        }

        return Pair(response.offers.map { it.toDomain() }, vacancies)
    }

    override suspend fun getFavoriteVacancies(): List<Vacancy> {
        return vacancyDao.getFavoriteVacancies().map { it.toDomain() }
    }

    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        vacancyDao.insertVacancy(vacancy.toEntity().copy(isFavorite = true))
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        vacancyDao.deleteVacancy(vacancy.toEntity())
    }
}