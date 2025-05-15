package com.example.aqbuddy.presentation.splash

import android.os.Handler
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.ui.SessionState
import com.example.aqbuddy.ui.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPref: MySharedPref,
    private val sessionStateHolder: SessionStateHolder
) : ViewModel() {

    init {
        checkAuth()
    }

    private fun checkAuth() {
        Handler().postDelayed({
            val isLoggedIn = sharedPref.getBoolPref(Constants.USER_LOGIN_KEY)

            sessionStateHolder.updateIsLoggedInState(
                if (isLoggedIn) SessionState.LoggedIn else SessionState.LoggedOut
            )
        }, 2000)
    }

}