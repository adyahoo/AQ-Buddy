package com.example.aqbuddy.domain.repository

import com.google.firebase.auth.AuthResult

interface FBaseAuthRepository {
    suspend fun register(email: String, password: String): AuthResult?
    suspend fun login(email: String, password: String): AuthResult?
}