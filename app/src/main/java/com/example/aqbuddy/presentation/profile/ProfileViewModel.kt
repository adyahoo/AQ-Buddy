package com.example.aqbuddy.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.model.User
import com.example.aqbuddy.domain.use_case.FBaseLogoutUseCase
import com.example.aqbuddy.domain.use_case.GetUserUseCase
import com.example.aqbuddy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: FBaseLogoutUseCase,
    private val userUseCase: GetUserUseCase
) : ViewModel() {
    var user = mutableStateOf<User?>(null)
    var isFetching = mutableStateOf(false)
    var isLoading = mutableStateOf(false)

    init {
        getProfile()
    }

    fun logout() {
        logoutUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    isLoading.value = true
                }

                is Resource.Success -> {
                    isLoading.value = false
                }

                is Resource.Error -> {
                    isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProfile() {
        userUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    isFetching.value = true
                }

                is Resource.Success -> {
                    isFetching.value = false
                    user.value = it.data
                }

                is Resource.Error -> {
                    isFetching.value = false
                }
            }
        }.launchIn(viewModelScope)
    }
}