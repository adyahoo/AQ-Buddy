package com.example.aqbuddy.domain.repository

import com.example.aqbuddy.data.remote.dto.FBaseState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FBaseAuthRepository {
    suspend fun register(email: String, password: String): AuthResult?
    suspend fun login(email: String, password: String): AuthResult?
}