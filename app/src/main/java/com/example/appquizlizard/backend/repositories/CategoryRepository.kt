package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.model.Category

interface CategoryRepository: BaseRepository<Category> {
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryById(categoryId: Int): Category?
    suspend fun getCategoryByName(name: String): Category?
}