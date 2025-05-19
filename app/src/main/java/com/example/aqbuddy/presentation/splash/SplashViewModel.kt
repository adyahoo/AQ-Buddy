package com.example.aqbuddy.presentation.splash

import android.os.Handler
import androidx.lifecycle.ViewModel
import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.ui.provider.session_provider.SessionState
import com.example.aqbuddy.ui.provider.session_provider.SessionStateHolder
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