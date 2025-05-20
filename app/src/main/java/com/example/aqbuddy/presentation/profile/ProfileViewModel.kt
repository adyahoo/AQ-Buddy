package com.example.aqbuddy.presentation.profile

import android.os.Handler
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.ui.provider.session.SessionState
import com.example.aqbuddy.ui.provider.session.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionStateHolder: SessionStateHolder,
    private val sharedPref: MySharedPref,
) : ViewModel() {
    var isLoading = mutableStateOf(false)

    fun logout() {
        isLoading.value = true

        Handler().postDelayed({
            sharedPref.setPref(Constants.USER_LOGIN_KEY, false)
            sessionStateHolder.updateIsLoggedInState(SessionState.LoggedOut)
            isLoading.value = false
        }, 1000)
    }
}