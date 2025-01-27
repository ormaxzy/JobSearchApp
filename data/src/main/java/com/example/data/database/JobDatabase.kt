package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.models.VacancyEntity

@Database(entities = [VacancyEntity::class], version = 1, exportSchema = false)
abstract class JobDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
