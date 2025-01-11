package com.vcoffee.catchio.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().reference

    private val _loginSuccess = MutableSharedFlow<Unit>()
    val loginSuccess: SharedFlow<Unit> = _loginSuccess.asSharedFlow()

    var activeTab by mutableStateOf("login")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var registerEmail by mutableStateOf("")
        private set

    var registerPassword by mutableStateOf("")
        private set

    var errorMessage by mutableStateOf<String?>(null)

    fun onTabChange(tab: String) {
        activeTab = tab
        Log.d("LoginViewModel", "Tab changed to: $tab")
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail.trim()
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onRegisterEmailChange(newEmail: String) {
        registerEmail = newEmail.trim()
    }

    fun onRegisterPasswordChange(newPassword: String) {
        registerPassword = newPassword
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Attempting login with email: $email")
                auth.signInWithEmailAndPassword(email, password).await()
                _loginSuccess.emit(Unit)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login failed: ${e.localizedMessage}", e)
                errorMessage = e.localizedMessage
            }
        }
    }

    fun onRegisterClicked() {
        val validationError = validatePassword(registerPassword)
        if (validationError != null) {
            errorMessage = validationError
            return
        }

        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Attempting registration with email: $registerEmail")
                val result = auth.createUserWithEmailAndPassword(registerEmail, registerPassword).await()
                val userId = result.user?.uid ?: throw Exception("User ID is null")

                val userMap = mapOf(
                    "email" to registerEmail,
                    "createdAt" to System.currentTimeMillis()
                )
                database.child("users").child(userId).setValue(userMap).await()

                Log.d("LoginViewModel", "Registration successful for userId: $userId")

                try {
                    Log.d("LoginViewModel", "Automatically logging in after registration")
                    auth.signInWithEmailAndPassword(registerEmail, registerPassword).await()
                    _loginSuccess.emit(Unit)
                } catch (loginException: Exception) {
                    Log.e("LoginViewModel", "Automatic login failed: ${loginException.localizedMessage}", loginException)
                    errorMessage = "Registration successful, but automatic login failed: ${loginException.localizedMessage}"
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Registration failed: ${e.localizedMessage}", e)
                errorMessage = e.localizedMessage
            }
        }
    }

    fun resetFields() {
        email = ""
        password = ""
        registerEmail = ""
        registerPassword = ""
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 6) return "Password must be at least 6 characters long"
        if (password.length > 30) return "Password must not exceed 30 characters"
        if (!password.any { it.isLowerCase() }) return "Password must contain at least one lowercase letter"
        if (!password.any { it.isUpperCase() }) return "Password must contain at least one uppercase letter"
        if (!password.any { it.isDigit() }) return "Password must contain at least one numeric digit"
        if (!password.any { it in "^$*.[]{}/()?!@#%&\\,><':;|_~" }) {
            return "Password must contain at least one non-alphanumeric character"
        }
        return null
    }
}