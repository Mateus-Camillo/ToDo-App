package com.example.todoapp.repositories

interface AuthRepository {
    suspend fun registerUser(email: String, password: String)

    fun emailValidation(email: String): Boolean

    fun passwordValidation(password: String): Boolean

    suspend fun loginUser(email: String, password: String)

    fun deleteUser(email: String)
}