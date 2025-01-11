package com.vcoffee.catchio.screens.login

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp

@Composable
fun LoginModal(
    viewModel: LoginViewModel,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TabButton(
                    title = "Login",
                    onClick = { viewModel.onTabChange("login") }
                )
                TabButton(
                    title = "Register",
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

            Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
                Text("Close")
            }
        }
    }
}

@Composable
fun TabButton(title: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = title)
    }
}

@Composable
fun LoginForm(viewModel: LoginViewModel, onLogin: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = viewModel.email,
            onValueChange = {
                viewModel.onEmailChange(it)
                Log.d("LoginForm", "Email updated: $it")
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.onPasswordChange(it)
                Log.d("LoginForm", "Password updated: $it")
            },
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
            onValueChange = {
                viewModel.onRegisterEmailChange(it)
                Log.d("RegisterForm", "Register Email updated: $it")
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = viewModel.registerPassword,
            onValueChange = {
                viewModel.onRegisterPasswordChange(it)
                Log.d("RegisterForm", "Register Password updated: $it")
            },
            label = { Text("Password (6-30 chars, lowercase, uppercase, digit, special char)") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRegister, modifier = Modifier.align(Alignment.End)) {
            Text("Register")
        }
    }
}
