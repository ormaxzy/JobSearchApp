package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_INPUT_PATTERN = "yyyy-MM-dd"
private const val DEFAULT_OUTPUT_PATTERN = "d MMMM"

object DateUtils {

    fun formatToReadableDate(
        dateString: String,
        inputPattern: String = DEFAULT_INPUT_PATTERN,
        outputPattern: String = DEFAULT_OUTPUT_PATTERN,
        locale: Locale = Locale("ru")
    ): String {
        return try {
            val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
            val outputFormat = SimpleDateFormat(outputPattern, locale)
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}