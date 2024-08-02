package com.example.android.weatherwiz.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.weatherwiz.data.model.CurrentWeatherEntity
import com.example.android.weatherwiz.data.model.HourlyWeatherEntity

@Database(entities = [CurrentWeatherEntity::class, HourlyWeatherEntity::class], exportSchema = false, version = 5)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}