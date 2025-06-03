package com.example.aqbuddy.presentation.login

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aqbuddy.domain.use_case.FBaseBiometricLoginUseCase
import com.example.aqbuddy.domain.use_case.FBaseLoginUseCase
import com.example.aqbuddy.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: FBaseLoginUseCase,
    private val biometricUseCase: FBaseBiometricLoginUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMsg by mutableStateOf("")

    var isShowPassword by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isSuccess by mutableStateOf(false)
    var isFingerprintEnabled by mutableIntStateOf(BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED)
    var isAuthenticating by mutableStateOf(false)

    private val executor: Executor = ContextCompat.getMainExecutor(context)

    private val biometricPromptCallback = object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)

            isAuthenticating = false
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)

            isAuthenticating = false
            biometricUseCase.invoke().onEach {
                when (it) {
                    is Resource.Success -> {
                        isAuthenticating = false
                        isSuccess = true
                    }

                    is Resource.Error -> {
                        isAuthenticating = false
                        errorMsg = it.error ?: "Unknown Error"
                    }

                    is Resource.Loading -> {
                        isAuthenticating = true
                    }
                }
            }.launchIn(viewModelScope)
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
        }
    }

    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Login with Fingerprint")
        .setSubtitle("Place your finger on the sensor")
        .setAllowedAuthenticators(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        .build()

    init {
        checkFingerprint()
    }

    private fun checkFingerprint() {
        val biometricManager = BiometricManager.from(context)

        isFingerprintEnabled = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        Log.d("isFingerprintEnabled", "$isFingerprintEnabled")
    }

    fun togglePasswordVisibility() {
        isShowPassword = !isShowPassword
    }

    fun loginBiometric(activity: FragmentActivity?) {
        Log.d("loginBiometric", "loginBiometric: $activity")
        activity?.let {
            BiometricPrompt(it, executor, biometricPromptCallback).authenticate(promptInfo)
        }
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