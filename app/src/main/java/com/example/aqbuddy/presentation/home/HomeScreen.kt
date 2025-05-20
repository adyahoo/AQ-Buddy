package com.example.aqbuddy.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aqbuddy.domain.model.BottomNavigationItem
import com.example.aqbuddy.presentation.dashboard.DashboardScreen
import com.example.aqbuddy.presentation.profile.ProfileScreen
import com.example.aqbuddy.utils.Screen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    var curIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
          Row {
              Text(text = "Home Screen")
              Button(onClick = { viewModel.logout() }) {
                  Text(text = "Logout")
              }
          }
        },
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().getBottomNavigationItems().forEachIndexed { i, item ->
                    NavigationBarItem(
                        selected = i == curIndex,
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        onClick = {
                            curIndex = i

                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.DashboardScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.DashboardScreen.route) {
                DashboardScreen(navController)
            }
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen(navController)
            }
        }
    }
}