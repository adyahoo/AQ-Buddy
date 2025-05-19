package com.example.aqbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.aqbuddy.presentation.home.HomeScreen
import com.example.aqbuddy.presentation.login.LoginScreen
import com.example.aqbuddy.presentation.map.MapScreen
import com.example.aqbuddy.presentation.register.RegisterScreen
import com.example.aqbuddy.presentation.splash.SplashScreen
import com.example.aqbuddy.ui.provider.session_provider.LocalSessionState
import com.example.aqbuddy.ui.provider.session_provider.SessionProvider
import com.example.aqbuddy.ui.provider.session_provider.SessionState
import com.example.aqbuddy.ui.provider.session_provider.SessionStateHolder
import com.example.aqbuddy.ui.theme.AppTheme
import com.example.aqbuddy.utils.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                                    navController.navigate(Screen.Authenticated) {
                                        popUpTo(Screen.SplashScreen) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                }
                            }

                            SessionState.LoggedOut -> {
                                LaunchedEffect(Unit) {
                                    navController.navigate(Screen.Unauthenticated) {
                                        popUpTo(Screen.SplashScreen) {
                                            inclusive = true
                                            saveState = false
                                        }
                                    }
                                }
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = Screen.SplashScreen,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable<Screen.SplashScreen> {
                                SplashScreen()
                            }
                            navigation<Screen.Authenticated>(startDestination = Screen.MapScreen) {
                                composable<Screen.HomeScreen> {
                                    HomeScreen(navController = navController)
                                }
                                composable<Screen.MapScreen> {
                                    MapScreen(navController = navController)
                                }
                            }
                            navigation<Screen.Unauthenticated>(startDestination = Screen.LoginScreen) {
                                composable<Screen.LoginScreen> {
                                    LoginScreen(navController = navController)
                                }
                                composable<Screen.RegisterScreen> {
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