package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.models.VacancyEntity

@Dao
interface VacancyDao {

    @Query("SELECT * FROM vacancies")
    suspend fun getAllVacancies(): List<VacancyEntity>

    @Query("SELECT * FROM vacancies WHERE isFavorite = 1")
    suspend fun getFavoriteVacancies(): List<VacancyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)
}