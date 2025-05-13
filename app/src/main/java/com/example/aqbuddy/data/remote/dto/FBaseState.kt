package com.example.aqbuddy.data.remote.dto

import com.google.firebase.auth.FirebaseUser

data class FBaseState(
    val data: FirebaseUser? = null,
    val errMessage: String? = null,
)