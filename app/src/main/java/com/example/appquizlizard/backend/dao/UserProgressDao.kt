package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.UserProgress

@Dao
interface UserProgressDao: BaseDao<UserProgress> {

    @Query("SELECT * FROM progress WHERE userId = :userId")
    suspend fun getUserProgress(userId: Int): List<UserProgress>

    @Query("SELECT COUNT(*) FROM progress WHERE userId = :userId AND isCorrect = 1")
    suspend fun getCorrectAnswersCount(userId: Int): Int

    // In UserProgressDao.kt
    @Query("SELECT * FROM progress WHERE categoryId = :categoryId AND userId = :userId LIMIT 1")
    suspend fun getUserProgressByCategoryAndUser(categoryId: Int, userId: Int): UserProgress?
}