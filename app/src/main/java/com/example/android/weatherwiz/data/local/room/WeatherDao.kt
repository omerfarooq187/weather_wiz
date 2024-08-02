package com.example.android.weatherwiz.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.weatherwiz.data.model.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherData(currentWeatherEntity: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather_table WHERE id = 1")
    fun getCurrentWeatherData(): Flow<CurrentWeatherEntity>
}