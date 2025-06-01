package com.example.appquizlizard.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appquizlizard.R

@Preview(showSystemUi = true)
@Composable
fun FinishScreenPreview() {
    FinishScreen(
        categoryId = 1,
        score = 7,
        navigateToMainScreen = {},
        playAgain = {}
    )
}

@Composable
fun FinishScreen(
    categoryId: Int,
    score: Int,
    navigateToMainScreen: () -> Unit,
    playAgain: (Int) -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFF"))
    val headerColor = Color(0xFF800020) // Burgundy color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top AppBar-style header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(headerColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "QuizLizard",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Main content with scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Quiz Completed!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = headerColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Display Score
            Text(
                text = "Your score is:",
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$score points",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = headerColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Trophy Image
            Image(
                painter = painterResource(id = R.drawable.quizlizardlogo),
                contentDescription = "Congratulations Image",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Feedback message
            val feedbackMessage = when {
                score > 8 -> "Excellent! You're a quiz master!"
                score > 5 -> "Great job! Keep learning!"
                else -> "Good effort! Try again to improve!"
            }

            Text(
                text = feedbackMessage,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Play Again Button
            Button(
                onClick = { playAgain(categoryId) },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text(
                    text = "Play Again",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Main Menu Button
            Button(
                onClick = { navigateToMainScreen() },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text(
                    text = "Main Menu",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
