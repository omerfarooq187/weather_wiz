package com.example.android.weatherwiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.weatherwiz.ui.screens.DetailsScreen
import com.example.android.weatherwiz.ui.screens.MainScreen
import com.example.android.weatherwiz.ui.theme.WeatherWizTheme
import com.example.android.weatherwiz.ui.theme.primaryContainerLight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherWizTheme {
                Scaffold(
                    containerColor = primaryContainerLight,
                    modifier = Modifier
                        .fillMaxSize()
                ) { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

//Screen routes
@Serializable
object MainScreenRoute

@Serializable
object DetailsScreenRoute


@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreenRoute) {
        composable<MainScreenRoute> {
            MainScreen(modifier = modifier)
        }
        composable<DetailsScreenRoute> {
            DetailsScreen(modifier = modifier)
        }
    }
}