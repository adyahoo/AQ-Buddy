package com.example.aqbuddy.data.repository

import com.example.aqbuddy.data.remote.dto.FBaseState
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FBaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : FBaseAuthRepository {
    private var user: FBaseState = FBaseState()

    override suspend fun register(email: String, password: String): AuthResult? {
        return auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun login(email: String, password: String): AuthResult? {
        return auth.signInWithEmailAndPassword(email, password).await()
    }

}