package com.example.aqbuddy.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppBar(
    navController: NavController,
    title: String,
    modifier: Modifier = Modifier,
    withBack: Boolean = false,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {}
) {
    Box(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        if (withBack) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Appbar Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )

        if (icon != null) {
            Spacer(modifier = Modifier.width(12.dp))
            IconButton(
                onClick = onIconClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    icon,
                    contentDescription = "Appbar Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
