package com.example.android.weatherwiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.weatherwiz.data.local.room.WeatherDao
import com.example.android.weatherwiz.data.model.CurrentWeatherEntity
import com.example.android.weatherwiz.data.model.HourlyWeatherEntity
import com.example.android.weatherwiz.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val weatherDao: WeatherDao
): ViewModel() {
    val weatherDetails = repository.weatherDetails
    init {
        viewModelScope.launch {
            repository.getWeatherDetailsFromApi()
        }

    }

    fun getCurrentWeather(): Flow<CurrentWeatherEntity> {
        return weatherDao.getCurrentWeatherData()
    }

    fun getHourlyWeather(): Flow<List<HourlyWeatherEntity>> {
        return weatherDao.getHourlyWeatherData()
    }
}