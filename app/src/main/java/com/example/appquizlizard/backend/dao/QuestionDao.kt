package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.Question

import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao: BaseDao<Question> {

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): Flow<List<Question>>

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    fun getQuestionByCategory(categoryId: Int): Flow<Question?>

    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?

    @Query("SELECT COUNT(*) FROM questions WHERE categoryId = :categoryId")
    suspend fun getQuestionCountByCategory(categoryId: Int): Int

    @Query("Select questionPictureResId FROM questions WHERE questionId = :questionId")
    suspend fun getQuestionPictureById(questionId: Int): Int

    @Query("SELECT * FROM questions WHERE categoryId = :categoryId")
    suspend fun getQuestionsByCategory(categoryId: Int): List<Question>


}