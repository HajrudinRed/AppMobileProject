package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.Answer


@Dao
interface AnswerDao: BaseDao<Answer> {

    @Query("SELECT * FROM Answer WHERE questionId = :questionId")
    suspend fun getAnswersForQuestion(questionId: Int): List<Answer>

    @Query("SELECT * FROM Answer WHERE questionId = :questionId AND isCorrect = 1 LIMIT 1")
    suspend fun getCorrectAnswer(questionId: Int): Answer?

}