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
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizViewModel @Inject constructor(
    private val questionRepository: QuestionRepository,
    private val answerRepository: AnswerRepository,
    private val userProgressRepository: UserProgressRepository
) : ViewModel() {

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion

    private val _answers = MutableStateFlow<List<Answer>>(emptyList())
    val answers: StateFlow<List<Answer>> = _answers

    private val _correctAnswer = MutableStateFlow<Answer?>(null)
    val correctAnswer: StateFlow<Answer?> = _correctAnswer

    private val _userProgress = MutableStateFlow<List<UserProgress>>(emptyList())
    val userProgress: StateFlow<List<UserProgress>> = _userProgress

    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount

    fun loadQuestion(questionId: Int) {
        viewModelScope.launch {
            val question = questionRepository.getQuestionById(questionId)
            _currentQuestion.value = question
            if (question != null) {
                _answers.value = answerRepository.getAnswersForQuestion(question.questionId)
                _correctAnswer.value = answerRepository.getCorrectAnswer(question.questionId)
            } else {
                _answers.value = emptyList()
                _correctAnswer.value = null
            }
        }
    }

    fun loadUserProgress(userId: Int) {
        viewModelScope.launch {
            _userProgress.value = userProgressRepository.getUserProgress(userId)
            _correctAnswersCount.value = userProgressRepository.getCorrectAnswersCount(userId)
        }
    }

    fun saveUserProgress(userId: Int, questionId: Int, isCorrect: Boolean) {
        viewModelScope.launch {
            val progress = UserProgress(
                userId = userId,
                questionId = questionId,
                isCorrect = isCorrect,
                timestamp = System.currentTimeMillis()
            )
            userProgressRepository.insert(progress)
            loadUserProgress(userId)
        }
    }
}
