package com.example.domain.models

data class Vacancy(
    val id: String,
    val lookingNumber: Int?,
    val title: String,
    val town: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salaryShort: String?,
    val salaryFull: String
)