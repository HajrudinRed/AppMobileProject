package com.example.appquizlizard.backend.repositories

import com.example.appquizlizard.backend.model.User

interface UserRepository : BaseRepository<User>{

    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
    suspend fun getUserById(id: Int): User?
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByUsername(username: String): User?

}