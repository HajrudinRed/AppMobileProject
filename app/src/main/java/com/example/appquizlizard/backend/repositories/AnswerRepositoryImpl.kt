package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.dao.AnswerDao
import com.example.appquizlizard.backend.model.Answer


import javax.inject.Inject

class AnswerRepositoryImpl @Inject constructor(private val answerDao: AnswerDao): AnswerRepository {

    override suspend fun insert(entity: Answer) {
        answerDao.insert(entity)
    }
    override suspend fun update(entity: Answer) {
        answerDao.update(entity)
    }
    override suspend fun delete(entity: Answer) {
        answerDao.delete(entity)
    }
    override suspend fun getAnswersForQuestion(questionId: Int): List<Answer>{
        return answerDao.getAnswersForQuestion(questionId)
    }
    override suspend fun getCorrectAnswer(questionId: Int): Answer?{
        return answerDao.getCorrectAnswer(questionId)
    }
}