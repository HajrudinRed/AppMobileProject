package com.example.appquizlizard.backend.repositories

import androidx.room.Query
import com.example.appquizlizard.backend.model.UserProgress

interface UserProgressRepository: BaseRepository<UserProgress> {

    suspend fun getUserProgress(userId: Int): List<UserProgress>
    suspend fun getCorrectAnswersCount(userId: Int): Int
    suspend fun getUserProgressByCategoryAndUser(categoryId: Int, userId: Int): UserProgress?



}




