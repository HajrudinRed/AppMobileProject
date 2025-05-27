package com.example.appquizlizard.backend.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.appquizlizard.backend.model.User

@Dao
interface UserDao : BaseDao<User>{

    @Query("SELECT * FROM users WHERE users.username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE users.email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend  fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}