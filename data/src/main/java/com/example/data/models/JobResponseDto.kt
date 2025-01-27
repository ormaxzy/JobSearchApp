package com.example.data.models

data class JobResponseDto(
    val offers: List<OfferDto>,
    val vacancies: List<VacancyDto>
)

data class OfferDto(
    val id: String?,
    val title: String,
    val button: ButtonDto?,
    val link: String
)

data class ButtonDto(
    val text: String
)

data class VacancyDto(
    val id: String,
    val lookingNumber: Int?,
    val title: String,
    val address: AddressDto,
    val company: String,
    val experience: ExperienceDto,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: Salary
)

data class Salary(
    val short: String?,
    val full: String
)

data class AddressDto(
    val town: String,
    val street: String?,
    val house: String?
)

data class ExperienceDto(
    val previewText: String,
    val text: String
)