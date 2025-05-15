package com.example.aqbuddy.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue

val LocalSessionState = compositionLocalOf<SessionState> {
    SessionState.Loading
}

@Composable
fun SessionProvider(sessionStateHolder: SessionStateHolder, content: @Composable () -> Unit) {
    val sessionState by sessionStateHolder.isLoggedIn.collectAsState()

    CompositionLocalProvider(
        LocalSessionState provides sessionState
    ) {
        content()
    }
}
