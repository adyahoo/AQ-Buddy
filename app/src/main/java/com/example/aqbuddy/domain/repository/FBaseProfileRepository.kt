package com.example.aqbuddy.domain.repository

import com.google.firebase.auth.FirebaseUser

interface FBaseProfileRepository {
    suspend fun updateProfile(name: String, user: FirebaseUser?): FirebaseUser
}