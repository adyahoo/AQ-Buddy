package com.example.aqbuddy.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aqbuddy.utils.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    ShowToast()
    ShowToastSuccess()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Login",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontStyle = MaterialTheme.typography.headlineLarge.fontStyle,
        )
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {
                viewModel.email = it
            },
            label = { Text("Email") },
            placeholder = { Text("Input your email here") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.password = it
            },
            label = { Text("Password") },
            placeholder = { Text("Input your password here") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            visualTransformation = if (!viewModel.isShowPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        viewModel.togglePasswordVisibility()
                    }
                ) {
                    val visibilityIcon = if (!viewModel.isShowPassword) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    Icon(
                        visibilityIcon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.login()
            }
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Text(
                    text = "Login",
                    fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            buildAnnotatedString {
                append("Don't have account yet? ")

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Create Account Now!")
                }
            },
            fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.RegisterScreen)
                }
        )
    }
}

@Composable
fun ShowToast(
    context: Context = LocalContext.current,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.errorMsg) {
        if (viewModel.errorMsg.isNotEmpty()) {
            Toast.makeText(context, viewModel.errorMsg, Toast.LENGTH_SHORT).show()
            viewModel.errorMsg = ""
        }
    }
}

@Composable
fun ShowToastSuccess(
    context: Context = LocalContext.current,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
            viewModel.isSuccess = false
        }
    }
}
