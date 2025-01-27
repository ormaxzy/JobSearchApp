package com.example.data.di

import com.example.data.JobRepositoryImpl
import com.example.data.api.JobSearchApi
import com.example.data.database.VacancyDao
import com.example.domain.JobRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideJobRepository(
        api: JobSearchApi,
        vacancyDao: VacancyDao
    ): JobRepository {
        return JobRepositoryImpl(
            api = api,
            vacancyDao = vacancyDao
        )
    }
}