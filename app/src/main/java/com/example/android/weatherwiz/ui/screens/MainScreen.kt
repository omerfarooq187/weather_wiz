package com.example.android.weatherwiz.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.android.weatherwiz.R
import com.example.android.weatherwiz.ui.theme.onPrimaryContainerLight
import com.example.android.weatherwiz.ui.theme.onSurfaceLight
import com.example.android.weatherwiz.ui.theme.primaryContainerLight
import com.example.android.weatherwiz.ui.theme.surfaceBrightLight
import com.example.android.weatherwiz.utils.LoadingProgressBar
import com.example.android.weatherwiz.viewmodel.WeatherViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val currentWeatherDetails = weatherViewModel.getCurrentWeather().collectAsState(initial = null)
    val weeklyWeatherDetails = weatherViewModel.weatherDetails.collectAsState()
    val lazyState = rememberLazyListState()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxSize()
            .background(primaryContainerLight)
            .padding(16.dp)
    ) {
        if (currentWeatherDetails.value != null) {
            val currentWeather = currentWeatherDetails.value!!
            //Top section of the screen
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = getDayNameFromEpochMillis(currentWeather.dateTimeEpoch * 1000),
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight(700),
                    fontSize = 18.sp,
                    color = onPrimaryContainerLight,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = getDateFromEpochMillis(currentWeather.dateTimeEpoch * 1000),
                    fontFamily = FontFamily(Font(R.font.opensans)),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Thin,
                    color = onPrimaryContainerLight,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                //Middle section of screen

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .background(surfaceBrightLight, RoundedCornerShape(20.dp))
                        .padding(8.dp)
                ) {
                    when (currentWeather.icon) {
                        "clear-day" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_clear_day_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }

                        "cloudy" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_cloudy_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }

                        "rain" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_rain_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }

                        "partly-cloudy-day" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_partly_cloudy_day_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }

                        "clear-night" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_clear_night_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }

                        "partly-cloudy-night" -> {
                            CurrentWeatherConditions(
                                resId = R.drawable.weather_partly_cloudy_night_pixel,
                                conditions = currentWeather.conditions,
                                temp = currentWeather.temp.toInt(),
                                feelsLike = currentWeather.feelsLike.toInt()
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 16.dp)
                    ) {
                        if (currentWeather.precip > 0) {
                            CurrentWeatherDetails(
                                resId = R.drawable.weather_rain_pixel,
                                value = "20%",
                                valueType = "Rain"
                            )
                        } else {
                            CurrentWeatherDetails(
                                resId = R.drawable.weather_clear_day_pixel,
                                value = "${currentWeather.pressure} hPa",
                                valueType = "Pressure"
                            )
                        }

                        CurrentWeatherDetails(
                            resId = R.drawable.weather_wind_pixel,
                            value = "${currentWeather.windSpeed.toInt()} km/h",
                            valueType = "Wind speed"
                        )

                        CurrentWeatherDetails(
                            resId = R.drawable.weather_humidity,
                            value = "${currentWeather.humidity}%",
                            valueType = "Humidity"
                        )
                    }
                }
                //Third Section of screen
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Today",
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryContainerLight
                    )
                    Text(
                        text = "Next 7 Days >",
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = onPrimaryContainerLight
                    )
                }
                val weatherDays = weeklyWeatherDetails.value?.days
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .background(surfaceBrightLight, RoundedCornerShape(20.dp))
                        .padding(8.dp)
                ) {
                    if (weatherDays != null) {
                        LazyRow(
                            state = lazyState,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val currentDayRemainingHours = 24 - ZonedDateTime.now().hour
                            val hours = weatherDays[0]
                                .hours
                                .takeLast(currentDayRemainingHours) + weatherDays[1]
                                .hours
                                .take(24 - currentDayRemainingHours)
                            items(hours) {
                                when (it.icon) {
                                    "rain" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_rain_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                    "clear-day" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_clear_day_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                    "cloudy" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_cloudy_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                    "partly-cloudy-day" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_partly_cloudy_day_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                    "clear-night" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_clear_night_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                    "partly-cloudy-night" -> {
                                        HourlyWeather(
                                            hourTime = getHourFromEpochMillis(it.datetimeEpoch * 1000),
                                            resId = R.drawable.weather_partly_cloudy_night_pixel,
                                            temp = it.temp.toInt()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } else {
            LoadingProgressBar()
        }
    }
}

fun getHoursFromApi() {
    val currentDayRemainingHours = 24 - ZonedDateTime.now().hour

}


fun getDayNameFromEpochMillis(epochMillis: Long, timeZone: String = "UTC"): String {
    val instant = Instant.ofEpochMilli(epochMillis)
    val zonedDateTime = instant.atZone(ZoneId.of(timeZone))
    return zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getDateFromEpochMillis(epochMillis: Long): String {
    val instant = Instant.ofEpochMilli(epochMillis)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    return zonedDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}

fun getHourFromEpochMillis(epochMillis: Long): String {
    val instant = Instant.ofEpochMilli(epochMillis)
    val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    return zonedDateTime.format(DateTimeFormatter.ofPattern("hh a"))
}

//Current weather conditions composable
@Composable
fun CurrentWeatherConditions(resId: Int, conditions: String, temp: Int, feelsLike: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "party cloudy",
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(220.dp)
        )
        Text(
            text = conditions,
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = "$temp°C",
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 24.sp,
            color = onSurfaceLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(8.dp)
        )
        Text(
            text = "Feels $feelsLike°C",
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 16.sp,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}


@Composable
fun CurrentWeatherDetails(resId: Int, value: String, valueType: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .width(110.dp)
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "",
            modifier = Modifier
                .padding(2.dp)
                .size(40.dp)
        )
        Text(
            text = value,
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(2.dp)
        )
        Text(
            text = valueType,
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 16.sp,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(2.dp)
        )
    }
}

@Composable
fun HourlyWeather(hourTime: String, resId: Int, temp: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourTime,
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 14.sp,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(4.dp)
        )
        Image(
            painter = painterResource(id = resId),
            contentDescription = "",
            modifier = Modifier
                .padding(4.dp)
                .size(40.dp)
        )
        Text(
            text = "$temp°C",
            fontFamily = FontFamily(Font(R.font.opensans)),
            fontSize = 16.sp,
            color = onSurfaceLight,
            modifier = Modifier
                .padding(4.dp)
        )
    }
}