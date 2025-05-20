package com.example.aqbuddy.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.aqbuddy.utils.Screen

data class BottomNavigationItem(
    val label:String = "",
    val icon: ImageVector = Icons.Default.Home,
    val route:String = "",
) {
    fun getBottomNavigationItems(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Dashboard",
                Icons.Default.Home,
                Screen.DashboardScreen.route
            ),
            BottomNavigationItem(
                label = "Profile",
                Icons.Default.AccountCircle,
                Screen.ProfileScreen.route
            ),
        )
    }
}
