package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.dao.UserDao
import com.example.appquizlizard.backend.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao): UserRepository {

    override suspend fun insert(entity: User) {
        userDao.insert(entity)
    }
    override suspend fun update(entity: User) {
        userDao.update(entity)
    }
    override suspend fun delete(entity: User) {
        userDao.delete(entity)
    }
    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }
    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }
    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
}