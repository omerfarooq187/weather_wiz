package com.example.android.weatherwiz.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.weatherwiz.data.model.CurrentWeatherEntity

@Database(entities = [CurrentWeatherEntity::class], exportSchema = false, version = 4)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}