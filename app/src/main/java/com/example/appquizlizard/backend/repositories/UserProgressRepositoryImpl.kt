package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.dao.UserProgressDao
import com.example.appquizlizard.backend.model.UserProgress
import javax.inject.Inject

class UserProgressRepositoryImpl @Inject constructor( private val userProgressDao: UserProgressDao): UserProgressRepository {

    override suspend fun insert(entity: UserProgress) {
        userProgressDao.insert(entity)
    }
    override suspend fun update(entity: UserProgress) {
        userProgressDao.update(entity)
    }
    override suspend fun delete(entity: UserProgress) {
        userProgressDao.delete(entity)
    }
    override suspend fun getUserProgress(userId: Int): List<UserProgress> {
        return userProgressDao.getUserProgress(userId)
    }
    override suspend fun getCorrectAnswersCount(userId: Int): Int {
        return userProgressDao.getCorrectAnswersCount(userId)
    }
    // In UserProgressDao.kt
    override suspend fun getUserProgressByCategoryAndUser(categoryId: Int, userId: Int): UserProgress? {
        return userProgressDao.getUserProgressByCategoryAndUser(categoryId, userId)
    }
}