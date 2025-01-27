package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.JobDatabase
import com.example.data.database.VacancyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "job_database"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): JobDatabase {
        return Room.databaseBuilder(
            context,
            JobDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideVacancyDao(database: JobDatabase): VacancyDao {
        return database.vacancyDao()
    }
}