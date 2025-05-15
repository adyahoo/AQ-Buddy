package com.example.aqbuddy.presentation.home

import androidx.lifecycle.ViewModel
import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.ui.SessionState
import com.example.aqbuddy.ui.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionStateHolder: SessionStateHolder,
    private val sharedPref: MySharedPref
) : ViewModel() {

    fun logout() {
        sharedPref.setPref(Constants.USER_LOGIN_KEY, false)
        sessionStateHolder.updateIsLoggedInState(SessionState.LoggedOut)
    }
}