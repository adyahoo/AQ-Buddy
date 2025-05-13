package com.example.aqbuddy.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseRegisterUseCase
import com.example.aqbuddy.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: FBaseRegisterUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isShowPassword by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isSuccess by mutableStateOf(false)
    var errorMsg by mutableStateOf("")

    fun togglePasswordVisibility() {
        isShowPassword = !isShowPassword
    }

    fun register() {
        isLoading = true

        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    isLoading = false
                    isSuccess = true
                }
                .addOnFailureListener {
                    isLoading = false
                    errorMsg = it.message ?: "Unknown Error"
                }
        } catch (e: Exception) {
            e.printStackTrace()
            isLoading = false
            errorMsg = e.localizedMessage ?: "An unexpected error occurred"
        }
    }
}