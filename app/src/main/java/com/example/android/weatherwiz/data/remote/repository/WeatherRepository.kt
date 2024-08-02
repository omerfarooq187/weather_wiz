package com.example.android.weatherwiz.data.remote.repository

import android.util.Log
import com.example.android.weatherwiz.data.local.room.WeatherDao
import com.example.android.weatherwiz.data.model.CurrentWeatherEntity
import com.example.android.weatherwiz.data.remote.WeatherApi
import com.example.android.weatherwiz.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) {
    private val _weatherDetails = MutableStateFlow<Weather?>(null)
    val weatherDetails: StateFlow<Weather?> = _weatherDetails
    val coroutine = CoroutineScope(Dispatchers.IO + SupervisorJob())
    suspend fun getWeatherDetailsFromApi() {
        weatherApi.getWeatherReport().enqueue(object : Callback<Weather?> {
            override fun onResponse(p0: Call<Weather?>, response: Response<Weather?>) {
                if (response.isSuccessful) {
                    _weatherDetails.value = response.body()
                    val currentConditions = _weatherDetails.value?.currentConditions
                    coroutine.launch {
                        if (currentConditions != null) {
                            weatherDao.insertCurrentWeatherData(
                                CurrentWeatherEntity(
                                    id = 1,
                                    dateTimeEpoch = currentConditions.datetimeEpoch,
                                    conditions = currentConditions.conditions,
                                    temp = currentConditions.temp,
                                    feelsLike = currentConditions.feelslike,
                                    pressure = currentConditions.pressure,
                                    windSpeed = currentConditions.windspeed,
                                    humidity = currentConditions.humidity,
                                    precip = currentConditions.precip,
                                    icon = currentConditions.icon,
                                )
                            )
                            Log.d("Current Conditions", "Database operation done")
                        }
                    }
                }
            }

            override fun onFailure(p0: Call<Weather?>, p1: Throwable) {
                p1.printStackTrace()
            }
        })

    }
}