package com.vcoffee.catchio.screens.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.atomic.AtomicInteger

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val usersCollection = db.collection("users")
    private val usernameCounter = AtomicInteger(0)

    private val sharedPref = getApplication<Application>().getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE)

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

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPref.edit().putBoolean("isLoggedIn", isLoggedIn).apply()
    }

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
                setLoggedIn(true)
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Login failed: ${e.message}", e)
                errorMessage = e.message
            }
        }
    }

    private suspend fun initializeUsernameCounter() {
        try {
            val count = usersCollection.get().await().size()
            usernameCounter.set(count)
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Failed to initialize username counter: ${e.message}", e)
            usernameCounter.set(0)
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

                val username = "Player${1000 + usernameCounter.incrementAndGet()}"

                val userMap = hashMapOf(
                    "userId" to userId,
                    "username" to username,
                    "email" to registerEmail,
                    "createdAt" to com.google.firebase.Timestamp.now()
                )

                usersCollection.document(userId).set(userMap).await()

                Log.d("LoginViewModel", "Registration successful for userId: $userId")

                try {
                    Log.d("LoginViewModel", "Automatically logging in after registration")
                    auth.signInWithEmailAndPassword(registerEmail, registerPassword).await()
                    _loginSuccess.emit(Unit)
                    setLoggedIn(true)
                } catch (loginException: Exception) {
                    Log.e("LoginViewModel", "Automatic login failed: ${loginException.message}", loginException)
                    errorMessage = "Registration successful, but automatic login failed: ${loginException.message}"
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Registration failed: ${e.message}", e)
                errorMessage = e.message
            }
        }
    }

    fun resetFields() {
        email = ""
        password = ""
        registerEmail = ""
        registerPassword = ""
    }

    fun logout() {
        auth.signOut()
        setLoggedIn(false)
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

    init {
        viewModelScope.launch {
            initializeUsernameCounter()
        }
    }
}