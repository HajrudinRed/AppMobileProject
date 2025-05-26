package com.example.appquizlizard.ui.theme.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.appquizlizard.R
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun SignUpScreen(
    navigateToMainScreen: (userId: Int) -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFFFF"))
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }

    val shape = RoundedCornerShape(
        topStart = CornerSize(0.dp),
        topEnd = CornerSize(0.dp),
        bottomStart = CornerSize(50.dp),
        bottomEnd = CornerSize(50.dp)
    )

    Column(
        modifier = Modifier
            .padding(0.dp)
            .background(color = backgroundColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF800020), shape = shape)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "QuizLizard",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        // Logo
        Image(
            painter = painterResource(id = R.drawable.quizlizardlogo),
            contentDescription = "Logo Image",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Username TextField
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username") },
            placeholder = { Text(text = "Enter your username") },
            shape = RoundedCornerShape(50.dp),
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
        )

        // Email TextField
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

        // Password TextField
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
        //Login if have account
        TextButton(
            onClick = { /* Navigate to Sign Up Screen */ },
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text(
                text = "Have an account?",
                fontSize = 16.sp,
                color = Color.DarkGray,
            )
        }
        Spacer(modifier = Modifier.size(width = 0.dp, height = 1.dp))
        // Sign Up Button
        Button(
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            shape = RoundedCornerShape(50.dp),
            onClick = { navigateToMainScreen(0) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF800020))
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navigateToMainScreen = {})
}