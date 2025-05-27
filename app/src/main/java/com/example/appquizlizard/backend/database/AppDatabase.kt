package com.example.appquizlizard.backend.database

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.appquizlizard.backend.dao.CategoryDao
import com.example.appquizlizard.backend.dao.QuestionDao
import com.example.appquizlizard.backend.dao.UserDao
import com.example.appquizlizard.backend.dao.UserProgressDao
import com.example.appquizlizard.backend.model.User
import com.example.appquizlizard.backend.model.Category
import com.example.appquizlizard.backend.model.Question
import com.example.appquizlizard.backend.model.UserProgress

@Database(
    entities = [User::class, Category::class, Question::class, UserProgress::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun questionDao(): QuestionDao
    abstract fun userProgressDao(): UserProgressDao

}