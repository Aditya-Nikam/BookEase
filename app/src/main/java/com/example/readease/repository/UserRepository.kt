package com.example.readease.repository

import com.example.readease.data.dao.UserDao
import com.example.readease.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun register(user: User) {
        userDao.register(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}