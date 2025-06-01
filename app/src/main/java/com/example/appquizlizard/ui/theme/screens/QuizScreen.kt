package com.example.appquizlizard.ui.theme.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appquizlizard.R
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.viewmodel.QuizViewModel

@Preview(showSystemUi = true)
@Composable
fun QuizScreenPreview() {
    // Create a mock/preview version that doesn't use the real viewModel
    QuizScreenContent(
        categoryId = 1,
        currentQuestion = Question(
            questionId = 1,
            categoryId = 1,
            questionText = "Sample question?",
            questionPictureResId = R.drawable.quizlizardlogo
        ),
        currentAnswers = listOf(
            Answer(1, 1, "Answer 1", true),
            Answer(2, 1, "Answer 2", false),
            Answer(3, 1, "Answer 3", false),
            Answer(4, 1, "Answer 4", false)
        ),
        selectedAnswerId = null,
        isAnswerCorrect = null,
        loading = false,
        error = null,
        correctAnswersCount = 0,
        onAnswerSelected = {},
        navigateToFinish = { _, _ -> }
    )
}

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
        onAnswerSelected = { answerId ->
            viewModel.selectAnswer(answerId)
        },
        navigateToFinish = navigateToFinish
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
    onAnswerSelected: (Int) -> Unit,
    navigateToFinish: (Int, Int) -> Unit
) {
    val backgroundColor = Color(android.graphics.Color.parseColor("#FFFFFF"))
    val headerColor = Color(0xFF800020) // Using direct color value instead of R.color.Red2
    val buttonColor = Color(0xFF800020) // Using direct color value instead of R.color.Green2

    // Track if we've already navigated to avoid duplicate navigations
    var hasNavigated by remember { mutableStateOf(false) }

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
                        text = "Score: $correctAnswersCount",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = headerColor,
                        modifier = Modifier.padding(vertical = 16.dp)
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

                                        // Wait for a moment before showing next question or finishing
                                        Handler(Looper.getMainLooper()).postDelayed({
                                            if (!hasNavigated) {
                                                hasNavigated = true
                                                navigateToFinish(categoryId, correctAnswersCount)
                                            }
                                        }, 1000)
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