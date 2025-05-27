package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.Category

@Dao
interface CategoryDao: BaseDao<Category> {
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<Category>

    @Query("Select * FROM categories WHERE name = :name;")
    suspend fun getCategoryByName(name: String): Category?

    @Query("SELECT * from categories where categoryId = :categoryId;")
    suspend fun getCategoryById(categoryId: Int): Category?
}