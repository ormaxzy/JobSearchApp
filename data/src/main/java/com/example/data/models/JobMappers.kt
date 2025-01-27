package com.example.data.models

import com.example.domain.models.Offer
import com.example.domain.models.Vacancy

fun OfferDto.toDomain(): Offer {
    return Offer(
        id = id,
        title = title,
        buttonText = button?.text,
        link = link
    )
}

fun VacancyDto.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        lookingNumber = lookingNumber,
        title = title,
        town = address.town,
        company = company,
        experience = experience.previewText,
        publishedDate = publishedDate,
        isFavorite = isFavorite,
        salaryFull = salary.full,
        salaryShort = salary.short
    )
}

fun JobResponseDto.toDomain(): Pair<List<Offer>, List<Vacancy>> {
    return Pair(
        offers.map { it.toDomain() },
        vacancies.map { it.toDomain() }
    )
}

fun VacancyDto.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        title = title,
        town = address.town,
        company = company,
        experience = experience.previewText,
        publishedDate = publishedDate,
        lookingNumber = lookingNumber,
        isFavorite = isFavorite,
        salaryShort = salary.short,
        salaryFull = salary.full
    )
}

fun VacancyEntity.toDomain(): Vacancy {
    return Vacancy(
        id = id,
        title = title,
        town = town,
        company = company,
        experience = experience,
        publishedDate = publishedDate,
        lookingNumber = lookingNumber,
        isFavorite = isFavorite,
        salaryShort = salaryShort,
        salaryFull = salaryFull.toString()
    )
}

fun Vacancy.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = id,
        title = title,
        town = town,
        company = company,
        experience = experience,
        publishedDate = publishedDate,
        lookingNumber = lookingNumber,
        isFavorite = isFavorite,
        salaryShort = salaryShort,
        salaryFull = salaryFull
    )
}