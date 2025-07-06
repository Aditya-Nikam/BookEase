package com.example.readease.data.dao

import androidx.room.*
import com.example.readease.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun register(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}