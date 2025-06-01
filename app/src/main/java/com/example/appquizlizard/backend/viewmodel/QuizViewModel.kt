package com.example.appquizlizard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appquizlizard.backend.model.Answer
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.repositories.AnswerRepository
import com.example.appquizlizard.backend.repositories.QuestionRepository
import com.example.appquizlizard.backend.repositories.UserProgressRepository
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

    // Expose current question as a Flow, emits new question when category or question changes
    val currentQuestion: StateFlow<Question?> = _categoryId
        .filterNotNull()
        .flatMapLatest { categoryId ->
            questionRepository.getQuestionByCategory(categoryId)
                .catch { emit(null) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

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

    // Track user's selected answer ID for current question
    private val _selectedAnswerId = MutableStateFlow<Int?>(null)
    val selectedAnswerId: StateFlow<Int?> = _selectedAnswerId.asStateFlow()

    // StateFlow to show if selected answer is correct or not (null if unanswered)
    private val _isAnswerCorrect = MutableStateFlow<Boolean?>(null)
    val isAnswerCorrect: StateFlow<Boolean?> = _isAnswerCorrect.asStateFlow()

    // Loading and error state (optional)
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Public function to set categoryId (e.g. when user picks a category)
    fun setCategory(categoryId: Int) {
        _categoryId.value = categoryId
        resetAnswerState()
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
                    com.example.appquizlizard.backend.model.UserProgress(
                        userId = 1,  // Replace with actual logged-in userId
                        questionId = question.questionId,
                        selectedAnswerId = answerId,           // <-- Pass selectedAnswerId here!
                        isCorrect = _isAnswerCorrect.value == true,
                        timestamp = System.currentTimeMillis() // You forgot timestamp as well!
                    )
                )
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    private fun resetAnswerState() {
        _selectedAnswerId.value = null
        _isAnswerCorrect.value = null
        _error.value = null
    }
}
