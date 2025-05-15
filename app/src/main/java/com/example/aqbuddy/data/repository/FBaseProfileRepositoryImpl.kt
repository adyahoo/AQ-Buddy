package com.example.aqbuddy.data.repository

import com.example.aqbuddy.domain.repository.FBaseProfileRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FBaseProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FBaseProfileRepository {
    override suspend fun updateProfile(name: String, user: FirebaseUser?): FirebaseUser {
        val updatedUser = userProfileChangeRequest {
            displayName = name
        }
        val currentUser = user ?: auth.currentUser

        currentUser?.updateProfile(updatedUser)?.await()

        return currentUser!!
    }
}