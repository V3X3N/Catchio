package com.example.catchio.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp

@Composable
fun LoginModal(
    viewModel: LoginViewModel,
    onClose: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onClose)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .align(Alignment.Center)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton(
                    title = "Login",
                    isActive = viewModel.activeTab == "login",
                    onClick = { viewModel.onTabChange("login") }
                )
                TabButton(
                    title = "Register",
                    isActive = viewModel.activeTab == "register",
                    onClick = { viewModel.onTabChange("register") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            if (viewModel.activeTab == "login") {
                LoginForm(viewModel = viewModel, onLogin = { viewModel.onLoginClicked() })
            } else {
                RegisterForm(viewModel = viewModel, onRegister = { viewModel.onRegisterClicked() })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onClose) {
                Text("Close")
            }
        }
    }
}

@Composable
fun TabButton(title: String, isActive: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(Color.Gray)
    ) {
        Text(text = title)
    }
}

@Composable
fun LoginForm(viewModel: LoginViewModel, onLogin: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = viewModel.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onLogin, modifier = Modifier.align(Alignment.End)) {
            Text("Log In")
        }
    }
}

@Composable
fun RegisterForm(viewModel: LoginViewModel, onRegister: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = viewModel.registerEmail,
            onValueChange = viewModel::onRegisterEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.registerPassword,
            onValueChange = viewModel::onRegisterPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegister, modifier = Modifier.align(Alignment.End)) {
            Text("Register")
        }
    }
}
