package com.example.appquizlizard.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appquizlizard.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.appquizlizard.backend.viewmodel.AuthViewModel
import com.example.appquizlizard.backend.viewmodel.LoginState
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    navigateToMainScreen: (Int) -> Unit,
    navigateToSignUp: () -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFFFF"))

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val loginState by authViewModel.loginState.collectAsState()

    // Add this LaunchedEffect
    LaunchedEffect(loginState.isSuccess) {
        if (loginState.isSuccess) {
            loginState.userId?.let { userId ->
                navigateToMainScreen(userId)
            }
        }
    }

    val shape = RoundedCornerShape(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
        bottomStart = CornerSize(50.dp),
        bottomEnd = CornerSize(50.dp)
    )





    Column(
        modifier = Modifier
            .padding(1.dp)
            .background(color = backgroundColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(Color(0xFF800020))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "QuizLizard",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(android.graphics.Color.parseColor("#FFFFFF")),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        Image(
            painter = painterResource(id = R.drawable.quizlizardlogo),
            contentDescription = "Logo Image",
            modifier = Modifier.size(225.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            placeholder = { Text(text = "example@example.com") },
            shape = RoundedCornerShape(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            placeholder = { Text(text = "Enter your password") },
            shape = RoundedCornerShape(50.dp),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    val icon = if (passwordVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (loginState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        loginState.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        if (loginState.isSuccess) {
            Text(
                text = "Login successful! User ID: ${loginState.userId}",
                color = Color.Green,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

// Don't have an account link
        TextButton(
            onClick = { navigateToSignUp() },
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text(
                text = "Don't have an account?",
                fontSize = 16.sp,
                color = Color.DarkGray,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(50.dp),
            onClick = {
                authViewModel.login(email.trim(), password.trim())
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF800020)),
            enabled = !loginState.isLoading
        ) {
            Text(
                text = "Login",
                color = Color.White,
                fontSize = 18.sp
            )

        }

    }
}
@Preview(showSystemUi = true, name = "Login Screen Preview")
@Composable
fun LoginScreenPreview() {
    // Create a mock AuthViewModel for preview
    val mockAuthViewModel = object : AuthViewModel() {
        override val loginState = MutableStateFlow(
            LoginState(
                isLoading = false,
                errorMessage = null,
                isSuccess = false,
                userId = null
            )
        )
    }

    LoginScreen(
        authViewModel = mockAuthViewModel,
        navigateToMainScreen = {},
        navigateToSignUp = {}
    )
}
