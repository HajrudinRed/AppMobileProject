package com.example.appquizlizard.backend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.model.UserProgress
import com.example.appquizlizard.backend.repositories.AnswerRepository
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.UserProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    // Current category id (you can set this from UI)
    private val _categoryId = MutableStateFlow<Int?>(null)

    private val _answers = MutableStateFlow<List<Answer>>(emptyList())
    val answers: StateFlow<List<Answer>> = _answers

    // Expose current question as a Flow, emits new question when category changes
    val currentQuestion: StateFlow<Question?> = _categoryId
        .filterNotNull()
        .flatMapLatest { categoryId ->
            questionRepository.getQuestionByCategory(categoryId)
                .catch { e ->
                    _error.value = e.message
                    emit(null)
                }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _correctAnswer = MutableStateFlow<Answer?>(null)
    val correctAnswer: StateFlow<Answer?> = _correctAnswer

    // Expose answers for the current question as a Flow
    val currentAnswers: StateFlow<List<Answer>> = currentQuestion
        .filterNotNull()
        .flatMapLatest { question ->
            flow {
                val answers = answerRepository.getAnswersForQuestion(question.questionId)
                emit(answers)
            }.catch { emit(emptyList()) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _userProgress = MutableStateFlow<List<UserProgress>>(emptyList())
    val userProgress: StateFlow<List<UserProgress>> = _userProgress

    // Track user's selected answer ID for current question
    private val _selectedAnswerId = MutableStateFlow<Int?>(null)
    val selectedAnswerId: StateFlow<Int?> = _selectedAnswerId.asStateFlow()

    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount

    // StateFlow to show if selected answer is correct or not (null if unanswered)
    private val _isAnswerCorrect = MutableStateFlow<Boolean?>(null)
    val isAnswerCorrect: StateFlow<Boolean?> = _isAnswerCorrect.asStateFlow()

    // Loading and error state
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Public function to set categoryId (e.g. when user picks a category)
    fun setCategory(categoryId: Int) {
        _categoryId.value = categoryId
        resetAnswerState()
    }

    fun loadUserProgress(userId: Int) {
        viewModelScope.launch {
            _userProgress.value = userProgressRepository.getUserProgress(userId)
            _correctAnswersCount.value = userProgressRepository.getCorrectAnswersCount(userId)
        }
    }

    // User selects an answer by answerId
    fun selectAnswer(answerId: Int) {
        _selectedAnswerId.value = answerId

        viewModelScope.launch {
            val question = currentQuestion.value
            if (question == null) {
                _error.value = "No question loaded."
                return@launch
            }

            _loading.value = true

            try {
                val correctAnswer = answerRepository.getCorrectAnswer(question.questionId)
                _isAnswerCorrect.value = (correctAnswer?.answerId == answerId)

                // Save user progress with correct parameters and timestamp
                userProgressRepository.insert(
                    UserProgress(
                        userId = 1,  // Replace with actual logged-in userId
                        questionId = question.questionId,
                        answerId = answerId,
                        isCorrect = _isAnswerCorrect.value == true,
                        timestamp = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveUserProgress(userId: Int, questionId: Int, isCorrect: Boolean) {
        viewModelScope.launch {
            val progress = UserProgress(
                userId = userId,
                questionId = questionId,
                answerId = _selectedAnswerId.value ?: 0,
                isCorrect = isCorrect,
                timestamp = System.currentTimeMillis()
            )
            userProgressRepository.insert(progress)
            loadUserProgress(userId)
        }
    }

    fun resetAnswerState() {
        _selectedAnswerId.value = null
        _isAnswerCorrect.value = null
        _error.value = null
    }
}