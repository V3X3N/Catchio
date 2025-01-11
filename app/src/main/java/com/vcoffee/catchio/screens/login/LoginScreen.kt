package com.vcoffee.catchio.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(onLoginSuccess: (FirebaseUser?) -> Unit) {
    val viewModel: LoginViewModel = viewModel()
    var isModalOpen by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        viewModel.loginSuccess.collect {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                onLoginSuccess(currentUser)
            }
        }
    }

    if (isModalOpen) {
        LoginModal(
            viewModel = viewModel,
            onClose = {
                isModalOpen = false
                viewModel.resetFields()
            }
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

    viewModel.errorMessage?.let { errorMessage ->
        AlertDialog(
            onDismissRequest = { viewModel.errorMessage = null },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                TextButton(onClick = { viewModel.errorMessage = null }) {
                    Text("OK")
                }
            }
        )
    }
}
