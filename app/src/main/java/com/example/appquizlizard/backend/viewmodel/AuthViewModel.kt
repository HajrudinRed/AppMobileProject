package com.example.appquizlizard.backend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val userId: Int? = null,
    val errorMessage: String? = null
)

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val userId: Int? = null,
    val errorMessage: String? = null
)

class AuthViewModel : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState: StateFlow<SignUpState> = _signUpState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)
            delay(1000) // simulate network

            if (email == "user@example.com" && password == "123456") {
                _loginState.value = LoginState(isSuccess = true, userId = 1)
            } else {
                _loginState.value = LoginState(isError = true, errorMessage = "Wrong email or password")
            }
        }
    }

    fun signUp(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpState.value = SignUpState(isLoading = true)
            delay(1000) // simulate network

            // Hardcoded "valid" credentials example
            if (username == "newuser" && email == "newuser@example.com" && password == "123456") {
                _signUpState.value = SignUpState(isSuccess = true, userId = 2)
            } else {
                _signUpState.value = SignUpState(isError = true, errorMessage = "Sign-up data invalid")
            }
        }
    }

    fun clearLoginState() {
        _loginState.value = LoginState()
    }

    fun clearSignUpState() {
        _signUpState.value = SignUpState()
    }
}
