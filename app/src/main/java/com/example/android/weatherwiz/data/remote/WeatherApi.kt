package com.example.android.weatherwiz.data.remote

import com.example.android.weatherwiz.model.Weather
import com.example.android.weatherwiz.utils.API_URL
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {
    @GET(API_URL)
    fun getWeatherReport(): Call<Weather>
}