package com.example.appquizlizard.backend.repositories


import com.example.appquizlizard.backend.dao.CategoryDao
import com.example.appquizlizard.backend.model.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryDao: CategoryDao): CategoryRepository {

    override suspend fun insert(entity: Category) {
        categoryDao.insert(entity)
    }
    override suspend fun update(entity: Category) {
        categoryDao.update(entity)
    }
    override suspend fun delete(entity: Category) {
        categoryDao.delete(entity)
    }
    override suspend fun getAllCategories(): List<Category>{
        return categoryDao.getAllCategories()
    }
    override suspend fun getCategoryByName(name: String): Category? {
        return categoryDao.getCategoryByName(name)
    }
    override suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDao.getCategoryById(categoryId)
    }
}