package com.vcoffee.catchio.screens

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow("Main Screen Content")
    val state: StateFlow<String> = _state

    init {
        updateLoggedInUser()
    }

    private fun updateLoggedInUser() {
        val user = FirebaseAuth.getInstance().currentUser
        _state.value = if (user != null) {
            "Logged in as: ${user.email ?: "Unknown Email"}"
        } else {
            "No user is currently logged in"
        }
    }
}
