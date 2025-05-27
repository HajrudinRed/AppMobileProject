package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao: BaseDao<Question> {

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    suspend fun getQuestionByCategory(categoryId: Int): Flow<Question>

    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?

    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId")
    suspend fun getQuestionCountByCategory(categoryId: Int): Int


}