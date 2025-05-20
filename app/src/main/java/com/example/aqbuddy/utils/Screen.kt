package com.example.aqbuddy.utils

sealed class Screen(val route: String) {
    // Session Management Route
     object SplashScreen: Screen("SplashScreen")

    // Unauthenticated Route
     object Unauthenticated: Screen("UnauthenticatedScreen")
     object LoginScreen: Screen("LoginScreen")
     object RegisterScreen: Screen("RegisterScreen")

    // Authenticated Route
     object Authenticated: Screen("AuthenticatedScreen")
     object HomeScreen: Screen("HomeScreen")
     object DashboardScreen: Screen("DashboardScreen")
     object ProfileScreen: Screen("ProfileScreen")
     object MapScreen: Screen("MapScreen")
}