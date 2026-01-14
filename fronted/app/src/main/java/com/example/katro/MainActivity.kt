/*package com.example.katro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.katro.screens.ConfigurationScreen
import com.example.katro.screens.GameScreen
import com.example.katro.screens.HomeScreen
import com.example.katro.screens.SplashScreen
import com.example.katro.ui.theme.KatroTheme

// Définition des routes de navigation
sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object Game : Screen("game_screen")
    object Settings : Screen("settings_screen")
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KatroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KatroApp()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun KatroApp(mainViewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Game.route) {
            GameScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(Screen.Settings.route) {
            ConfigurationScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}
*/
package com.example.katro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.katro.screen.ApplicationJeuNoix
import com.example.katro.ui.theme.KatroTheme
import com.example.katro.viewModel.JeuViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KatroTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 1. On crée l'instance du ViewModel ici
                    val monJeuViewModel: JeuViewModel = viewModel()

                    // 2. On passe cette instance à ApplicationJeuNoix
                    // L'erreur rouge disparaît car le paramètre est maintenant rempli !
                    ApplicationJeuNoix(monViewModel = monJeuViewModel)
                }
            }
        }
    }
}