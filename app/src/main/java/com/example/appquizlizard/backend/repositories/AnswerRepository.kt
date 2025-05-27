package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.model.Answer


interface AnswerRepository : BaseRepository<Answer> {
    suspend fun getAnswersForQuestion(questionId: Int): List<Answer>
    suspend fun getCorrectAnswer(questionId: Int): Answer?
}