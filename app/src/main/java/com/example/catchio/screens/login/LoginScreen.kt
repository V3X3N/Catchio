package com.example.catchio.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModel: LoginViewModel = viewModel()
    val lifecycleScope = rememberCoroutineScope()
    var isModalOpen by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        lifecycleScope.launch {
            viewModel.loginSuccess.collect {
                onLoginSuccess()
            }
        }
    }

    if (isModalOpen) {
        LoginModal(
            viewModel = viewModel,
            onClose = { isModalOpen = false },
            onLoginSuccess = onLoginSuccess
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { isModalOpen = true }) {
                Text("Open Modal")
            }
        }
    }
}
