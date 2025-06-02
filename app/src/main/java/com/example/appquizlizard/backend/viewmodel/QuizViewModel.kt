package com.example.appquizlizard.backend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.model.UserProgress
import com.example.appquizlizard.backend.repositories.AnswerRepository
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.UserProgressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.insert

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    // Current category
    private val _categoryId = MutableStateFlow<Int?>(null)

    // List of all questions for the selected category
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions = _questions.asStateFlow()

    // Current question index
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex = _currentQuestionIndex.asStateFlow()

    // Total number of questions
    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions = _totalQuestions.asStateFlow()

    // Current question
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion = _currentQuestion.asStateFlow()

    // Current answers for the question
    private val _currentAnswers = MutableStateFlow<List<Answer>>(emptyList())
    val currentAnswers = _currentAnswers.asStateFlow()

    // Selected answer ID
    private val _selectedAnswerId = MutableStateFlow<Int?>(null)
    val selectedAnswerId = _selectedAnswerId.asStateFlow()

    // Is the answer correct
    private val _isAnswerCorrect = MutableStateFlow<Boolean?>(null)
    val isAnswerCorrect = _isAnswerCorrect.asStateFlow()

    // Loading state
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Track the user's score
    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount = _correctAnswersCount.asStateFlow()

    // User progress
    private val _userProgress = MutableStateFlow<UserProgress?>(null)
    val userProgress = _userProgress.asStateFlow()

    fun setCategory(categoryId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _categoryId.value = categoryId

            try {
                // Load all questions for this category
                val questions = questionRepository.getQuestionsByCategory(categoryId)
                _questions.value = questions
                _totalQuestions.value = questions.size

                if (questions.isNotEmpty()) {
                    _currentQuestion.value = questions.first()
                    loadAnswersForCurrentQuestion()
                } else {
                    _error.value = "No questions found for this category"
                }
            } catch (e: Exception) {
                _error.value = "Error loading questions: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    // Load answers for the current question
    private fun loadAnswersForCurrentQuestion() {
        viewModelScope.launch {
            _currentQuestion.value?.let { question ->
                _currentAnswers.value = answerRepository.getAnswersForQuestion(question.questionId)
            }
        }
    }

    fun selectAnswer(answerId: Int) {
        viewModelScope.launch {
            if (_selectedAnswerId.value == null) {
                _selectedAnswerId.value = answerId

                // Check if the answer is correct
                val correctAnswer = answerRepository.getCorrectAnswer(_currentQuestion.value?.questionId ?: 0)
                val isCorrect = correctAnswer?.answerId == answerId
                _isAnswerCorrect.value = isCorrect

                // Update score if correct
                if (isCorrect) {
                    _correctAnswersCount.value = _correctAnswersCount.value + 1
                }

                // Update user progress
                updateUserProgress()
            }
        }
    }

    // Move to the next question
    fun moveToNextQuestion() {
        val nextIndex = _currentQuestionIndex.value + 1
        if (nextIndex < _questions.value.size) {
            _currentQuestionIndex.value = nextIndex
            _currentQuestion.value = _questions.value[nextIndex]
            resetAnswerState()
            loadAnswersForCurrentQuestion()
        }
    }

    // Reset answer state for next question
    private fun resetAnswerState() {
        _selectedAnswerId.value = null
        _isAnswerCorrect.value = null
    }

    // Load user progress
    fun loadUserProgress(userId: Int) {
        viewModelScope.launch {
            try {
                val categoryId = _categoryId.value ?: return@launch
                val progress = userProgressRepository.getUserProgressByCategoryAndUser(categoryId, userId)
                _userProgress.value = progress
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    // Update user progress
    private fun updateUserProgress() {
        viewModelScope.launch {
            try {
                val categoryId = _categoryId.value ?: return@launch
                val userId = 1 // Replace with actual user ID in production
                val questionId = _currentQuestion.value?.questionId
                val answerId = _selectedAnswerId.value
                val isCorrect = _isAnswerCorrect.value ?: false

                // Create or update user progress
                val existingProgress = userProgressRepository.getUserProgressByCategoryAndUser(categoryId, userId)

                if (existingProgress != null) {
                    // Create updated progress with new score
                    val updatedProgress = existingProgress.copy(
                        score = _correctAnswersCount.value,
                        questionId = questionId ?: 0, // Use 0 as default if questionId is null
                        answerId = answerId ?: 0, // Use 0 as default if answerId is null
                        isCorrect = isCorrect,
                        timestamp = System.currentTimeMillis()
                    )
                    userProgressRepository.update(updatedProgress)
                } else {
                    // Create new progress
                    val newProgress = UserProgress(
                        userId = userId,
                        categoryId = categoryId,
                        score = _correctAnswersCount.value,
                        questionId = questionId ?: 0, // Use 0 as default if questionId is null
                        answerId = answerId ?: 0, // Use 0 as default if answerId is null
                        isCorrect = isCorrect,
                        timestamp = System.currentTimeMillis()
                    )
                    userProgressRepository.insert(newProgress)
                }

                _userProgress.value = existingProgress
            } catch (e: Exception) {
                _error.value = "Failed to update progress: ${e.message}"
            }
        }
    }

    fun getQuestionsByCategory(categoryId: Int) {
        viewModelScope.launch {
            try {
                val questions = questionRepository.getQuestionsByCategory(categoryId)
                _questions.value = questions
                _totalQuestions.value = questions.size
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}