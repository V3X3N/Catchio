package com.example.catchio.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
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

    fun onTabChange(tab: String) {
        activeTab = tab
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onRegisterEmailChange(newEmail: String) {
        registerEmail = newEmail
    }

    fun onRegisterPasswordChange(newPassword: String) {
        registerPassword = newPassword
    }

    fun onLoginClicked() {
        viewModelScope.launch {
            _loginSuccess.emit(Unit)
        }
    }

    fun onRegisterClicked() {
        viewModelScope.launch {
            _loginSuccess.emit(Unit)
        }
    }
}
