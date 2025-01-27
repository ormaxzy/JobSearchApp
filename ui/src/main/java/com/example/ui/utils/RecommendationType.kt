package com.example.ui.utils

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.example.ui.R


enum class RecommendationType(
    val id: String,
    @DrawableRes val iconRes: Int,
    @ColorRes val backgroundRes: Int
) {
    NEAR_VACANCIES("near_vacancies", R.drawable.near_vacancies, R.color.dark_blue),
    LEVEL_UP_RESUME("level_up_resume", R.drawable.level_up_resume, R.color.dark_green),
    TEMPORARY_JOB("temporary_job", R.drawable.temporary_job, R.color.dark_green),
    UNKNOWN("", 0, 0);

    companion object {
        fun fromId(id: String): RecommendationType {
            return entries.find { it.id == id } ?: UNKNOWN
        }
    }
}