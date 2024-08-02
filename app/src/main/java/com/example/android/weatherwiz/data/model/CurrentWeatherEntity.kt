package com.example.android.weatherwiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather_table")
data class CurrentWeatherEntity(
    @PrimaryKey
    val id: Int,
    val dateTimeEpoch: Long,
    val conditions: String,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Double,
    val precip: Double,
    val icon: String,
    val timeStamp: Long = System.currentTimeMillis()
)
