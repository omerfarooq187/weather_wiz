package com.example.android.weatherwiz.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hourly_weather")
data class HourlyWeatherEntity(
    @PrimaryKey
    val id: Int,
    val dateTimeEpoch: Long,
    val conditions: String,
    val temp: Double,
    val icon:String
)