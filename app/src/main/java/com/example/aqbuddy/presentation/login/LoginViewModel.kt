package com.example.aqbuddy.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseLoginUseCase
import com.example.aqbuddy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FBaseLoginUseCase,
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

    fun login() {
        loginUseCase(email, password).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    isLoading = false
                    isSuccess = true
                }
                is Resource.Error -> {
                    isLoading = false
                    errorMsg = res.error ?: "Unknown Error"
                }
                is Resource.Loading -> {
                    isLoading = true
                }
            }
        }.launchIn(viewModelScope)
    }
}