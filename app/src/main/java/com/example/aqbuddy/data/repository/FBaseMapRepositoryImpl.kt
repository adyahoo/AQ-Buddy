package com.example.aqbuddy.data.repository

import com.example.aqbuddy.domain.repository.FBaseMapRepository
import com.example.aqbuddy.utils.Constants
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FBaseMapRepositoryImpl : FBaseMapRepository {
    private val firestore = Firebase.firestore

    override suspend fun getNearbyAqi(): QuerySnapshot? {
        return firestore.collection(Constants.COLLECTION_NAME)
            .get()
            .await()
    }
}