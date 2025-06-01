package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository : BaseRepository<Question>{

    suspend fun getAllQuestions(): Flow<List<Question>>
    suspend fun getQuestionByCategory(categoryId: Int): Flow<Question?>
    suspend fun getQuestionById(questionId: Int): Question?
    suspend fun getQuestionCountByCategory(categoryId: Int): Int
}