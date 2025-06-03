package com.example.aqbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.aqbuddy.presentation.dashboard.DashboardScreen
import com.example.aqbuddy.presentation.home.HomeScreen
import com.example.aqbuddy.presentation.login.LoginScreen
import com.example.aqbuddy.presentation.map.MapScreen
import com.example.aqbuddy.presentation.profile.ProfileScreen
import com.example.aqbuddy.presentation.register.RegisterScreen
import com.example.aqbuddy.presentation.splash.SplashScreen
import com.example.aqbuddy.ui.provider.session.LocalSessionState
import com.example.aqbuddy.ui.provider.session.SessionProvider
import com.example.aqbuddy.ui.provider.session.SessionState
import com.example.aqbuddy.ui.provider.session.SessionStateHolder
import com.example.aqbuddy.ui.theme.AppTheme
import com.example.aqbuddy.utils.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionStateHolder: SessionStateHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    SessionProvider(sessionStateHolder) {
                        val localSessionState = LocalSessionState.current

                        when (localSessionState) {
                            SessionState.Loading -> {}

                            SessionState.LoggedIn -> {
                                LaunchedEffect(Unit) {
                                    navController.navigate(Screen.Authenticated.route) {
                                        popUpTo(Screen.SplashScreen.route) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                }
                            }

                            SessionState.LoggedOut -> {
                                LaunchedEffect(Unit) {
                                    navController.navigate(Screen.Unauthenticated.route) {
                                        popUpTo(Screen.SplashScreen.route) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                }
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(route = Screen.SplashScreen.route) {
                                SplashScreen()
                            }
                            navigation(
                                route = Screen.Authenticated.route,
                                startDestination = Screen.DashboardScreen.route
                            ) {
                                composable(route = Screen.DashboardScreen.route) {
                                    DashboardScreen(navController)
                                }
                                composable(route = Screen.ProfileScreen.route) {
                                    ProfileScreen(navController)
                                }
                                composable(route = Screen.MapScreen.route) {
                                    MapScreen(navController = navController)
                                }
                            }
                            navigation(
                                route = Screen.Unauthenticated.route,
                                startDestination = Screen.LoginScreen.route
                            ) {
                                composable(route = Screen.LoginScreen.route) {
                                    LoginScreen(navController = navController)
                                }
                                composable(route = Screen.RegisterScreen.route) {
                                    RegisterScreen(navController = navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
