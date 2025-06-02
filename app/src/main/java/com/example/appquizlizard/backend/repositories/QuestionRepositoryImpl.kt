package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.dao.QuestionDao
import com.example.appquizlizard.backend.model.Question
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(private val questionDao: QuestionDao): QuestionRepository {

    override suspend fun insert(entity: Question) {
        questionDao.insert(entity)
    }
    override suspend fun update(entity: Question) {
        questionDao.update(entity)
    }
    override suspend fun delete(entity: Question) {
        questionDao.delete(entity)
    }
    override suspend fun getAllQuestions(): Flow<List<Question>> {
        return questionDao.getAllQuestions()
    }
    override suspend fun getQuestionByCategory(id: Int): Flow<Question?> {
        return questionDao.getQuestionByCategory(categoryId = id)
    }
    override suspend fun getQuestionById(id: Int): Question? {
        return questionDao.getQuestionById(questionId = id) // or just Id not sure need to chack later if it doeasnt work!
    }
    override suspend fun getQuestionCountByCategory(categoryId: Int): Int {
        return questionDao.getQuestionCountByCategory(categoryId)
    }
    override suspend fun getQuestionsByCategory(categoryId: Int): List<Question> {
        return questionDao.getQuestionsByCategory(categoryId)
    }
}