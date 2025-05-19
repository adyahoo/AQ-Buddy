package com.example.aqbuddy.domain.repository

import com.google.firebase.firestore.QuerySnapshot

interface FBaseMapRepository {
    suspend fun getNearbyAqi(): QuerySnapshot?
}