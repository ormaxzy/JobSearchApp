package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey val id: String,
    val title: String,
    val town: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
    val lookingNumber: Int?,
    val isFavorite: Boolean,
    val salaryShort: String?,
    val salaryFull: String?
)