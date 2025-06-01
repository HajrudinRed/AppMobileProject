package com.example.appquizlizard.backend.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appquizlizard.backend.model.User
import com.example.appquizlizard.backend.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Patterns
import java.lang.Exception

data class LoginState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userId: Int? = null
)

data class SignUpState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val userId: Int? = null
)

open class AuthViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    open val loginState: StateFlow<LoginState> = _loginState

    private val _signUpState = MutableStateFlow(SignUpState())
    open val signUpState: StateFlow<SignUpState> = _signUpState

    // Use our custom repository implementation
    private val userRepository = InMemoryUserRepository()

    fun login(email: String, password: String) {
        _loginState.value = LoginState(isLoading = true)

        viewModelScope.launch {
            try {
                // Validate email format
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    _loginState.value = LoginState(errorMessage = "Please enter a valid email address")
                    return@launch
                }

                // Validate password length
                if (password.length < 6) {
                    _loginState.value = LoginState(errorMessage = "Password must be at least 6 characters long")
                    return@launch
                }

                val user = withContext(Dispatchers.IO) {
                    userRepository.getUserByEmailAndPassword(email, password)
                }

                if (user != null) {
                    _loginState.value = LoginState(
                        isLoading = false,
                        isSuccess = true,
                        userId = user.userId
                    )
                } else {
                    _loginState.value = LoginState(
                        isLoading = false,
                        errorMessage = "Invalid email or password"
                    )
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login error: ${e.message}")
                _loginState.value = LoginState(
                    isLoading = false,
                    errorMessage = "Error logging in: ${e.message}"
                )
            }
        }
    }

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = SignUpState(isLoading = true)

            try {
                // Validate inputs
                val validationError = validateSignUpInputs(username, email, password)
                if (validationError != null) {
                    _signUpState.value = SignUpState(errorMessage = validationError)
                    return@launch
                }

                // Check if user with email already exists
                val existingUser = userRepository.getUserByEmail(email)
                if (existingUser != null) {
                    _signUpState.value = SignUpState(errorMessage = "Email already in use")
                    return@launch
                }

                // Create new user
                val newUser = User(
                    userId = 0, // This will be assigned in the repository
                    username = username,
                    email = email,
                    password = password
                )

                // Insert the user
                userRepository.insert(newUser)

                // Get the user back to obtain the ID
                val createdUser = withContext(Dispatchers.IO) {
                    userRepository.getUserByEmail(email)
                }

                // Return success with the user ID
                _signUpState.value = SignUpState(
                    isLoading = false,
                    isSuccess = true,
                    userId = createdUser?.userId
                )
                Log.d("AuthViewModel", "User registered successfully with ID: ${createdUser?.userId}")
            } catch (e: Exception) {
                Log.e("AuthViewModel", "SignUp error: ${e.message}", e)
                _signUpState.value = SignUpState(
                    isLoading = false,
                    errorMessage = "Error creating account: ${e.message}"
                )
            }
        }
    }
    private fun validateSignUpInputs(username: String, email: String, password: String): String? {
        if (username.isBlank()) {
            return "Username is required"
        }
        if (email.isBlank()) {
            return "Email is required"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Please enter a valid email address"
        }
        if (password.isBlank()) {
            return "Password is required"
        }
        if (password.length < 6) {
            return "Password must be at least 6 characters long"
        }
        return null
    }
    private interface BaseRepository<T> {
        suspend fun insert(entity: T)
        suspend fun update(entity: T)
        suspend fun delete(entity: T)
    }

    fun clearLoginState() {
        _loginState.value = LoginState()
    }

    fun clearSignUpState() {
        _signUpState.value = SignUpState()
    }

    // Simple in-memory repository for testing
    private inner class InMemoryUserRepository : UserRepository {
        private val users = mutableListOf(
            User(userId = 1, username = "test", email = "test@example.com", password = "password")
        )

        override suspend fun insert(entity: User){
            val newId = if (users.isEmpty()) 1 else users.maxOf { it.userId } + 1
            val userWithId = entity.copy(userId = newId)
            users.add(userWithId)
        }

        override suspend fun update(entity: User) {
            val index = users.indexOfFirst { it.userId == entity.userId }
            if (index != -1) {
                users[index] = entity
            }
        }

        override suspend fun delete(entity: User) {
            users.removeIf { it.userId == entity.userId }
        }

        override suspend fun getUserByEmail(email: String): User? {
            return users.find { it.email.equals(email, ignoreCase = true) }
        }

        override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
            return users.find {
                it.email.equals(email, ignoreCase = true) && it.password == password
            }
        }

        override suspend fun getUserById(id: Int): User? {
            return users.find { it.userId == id }
        }

        override suspend fun getAllUsers(): List<User> {
            return users.toList()
        }

        override suspend fun getUserByUsername(username: String): User? {
            return users.find { it.username.equals(username, ignoreCase = true) }
        }
    }
}