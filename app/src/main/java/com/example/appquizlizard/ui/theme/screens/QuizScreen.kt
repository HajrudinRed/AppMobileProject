package com.example.appquizlizard.ui.theme.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    categoryId: Int,
    navigateToFinish: (Int, Int) -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    // Collect state from ViewModel
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val currentAnswers by viewModel.currentAnswers.collectAsState()
    val selectedAnswerId by viewModel.selectedAnswerId.collectAsState()
    val isAnswerCorrect by viewModel.isAnswerCorrect.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val correctAnswersCount by viewModel.correctAnswersCount.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val totalQuestions by viewModel.totalQuestions.collectAsState()

    // Call setCategory when the screen is first created
    LaunchedEffect(key1 = categoryId) {
        viewModel.setCategory(categoryId)
        viewModel.loadUserProgress(1) // Replace with actual user ID
    }

    QuizScreenContent(
        categoryId = categoryId,
        currentQuestion = currentQuestion,
        currentAnswers = currentAnswers,
        selectedAnswerId = selectedAnswerId,
        isAnswerCorrect = isAnswerCorrect,
        loading = loading,
        error = error,
        correctAnswersCount = correctAnswersCount,
        currentQuestionIndex = currentQuestionIndex,
        totalQuestions = totalQuestions,
        onAnswerSelected = { answerId ->
            viewModel.selectAnswer(answerId)
        },
        navigateToFinish = navigateToFinish,
        viewModel = viewModel
    )
}

@Composable
fun QuizScreenContent(
    categoryId: Int,
    currentQuestion: Question?,
    currentAnswers: List<Answer>,
    selectedAnswerId: Int?,
    isAnswerCorrect: Boolean?,
    loading: Boolean,
    error: String?,
    correctAnswersCount: Int,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onAnswerSelected: (Int) -> Unit,
    navigateToFinish: (Int, Int) -> Unit,
    viewModel: QuizViewModel
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFF"))
    val headerColor = Color(0xFF800020)
    val buttonColor = Color(0xFF800020)

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

        // Main Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (loading) {
                CircularProgressIndicator(color = headerColor)
            } else if (error != null) {
                Text(
                    text = "Error: $error",
                    color = Color.Red,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (currentQuestion == null) {
                Text(
                    text = "No questions available for this category",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Question Number/Score
                    Text(
                        text = "Question ${currentQuestionIndex + 1}/$totalQuestions",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Text(
                        text = "Score: $correctAnswersCount",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = headerColor,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Question Image
                    currentQuestion.questionPictureResId.let { resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(vertical = 16.dp)
                        )
                    }

                    // Question Text
                    Text(
                        text = currentQuestion.questionText,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Answer Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        currentAnswers.forEach { answer ->
                            val isSelected = selectedAnswerId == answer.answerId
                            val buttonBackgroundColor = when {
                                !isSelected -> buttonColor
                                isAnswerCorrect == true -> Color.Green
                                else -> Color.Red
                            }

                            Button(
                                onClick = {
                                    if (selectedAnswerId == null) {
                                        onAnswerSelected(answer.answerId)

                                        // Wait for a moment before moving to next question
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            // Check if this was the last question
                                            if (currentQuestionIndex + 1 < totalQuestions) {
                                                viewModel.moveToNextQuestion()
                                            } else {
                                                // Navigate to finish if all questions answered
                                                navigateToFinish(categoryId, correctAnswersCount)
                                            }
                                        }, 1000) // Show answer state for 1 second
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(25.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonBackgroundColor
                                ),
                                enabled = selectedAnswerId == null
                            ) {
                                Text(
                                    text = answer.text,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}