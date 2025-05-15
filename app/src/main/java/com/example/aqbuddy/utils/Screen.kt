package com.example.aqbuddy.utils

import kotlinx.serialization.Serializable

object Screen {
    // Session Management Route
    @Serializable object SplashScreen

    // Unauthenticated Route
    @Serializable object Unauthenticated
    @Serializable object LoginScreen
    @Serializable object RegisterScreen

    // Authenticated Route
    @Serializable object Authenticated
    @Serializable object HomeScreen
    @Serializable object MapScreen
}