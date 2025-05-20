package com.example.aqbuddy.ui.provider.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

sealed class SessionState {
    object Loading : SessionState()
    object LoggedIn : SessionState()
    object LoggedOut : SessionState()
}

class SessionStateHolder {
    private val _isLoggedIn = MutableStateFlow<SessionState>(SessionState.Loading)
    val isLoggedIn: StateFlow<SessionState> = _isLoggedIn

    fun updateIsLoggedInState(state: SessionState) {
        _isLoggedIn.update { state }
    }
}
