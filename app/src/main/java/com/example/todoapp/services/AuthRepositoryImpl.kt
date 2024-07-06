package com.example.todoapp.services

import com.example.todoapp.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun registerUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun emailValidation(email: String): Boolean {

        val specialCharacters: List<String> = listOf("@", ".")

        val requirement = Pair("Valid email address", specialCharacters.all {char -> email.contains(char)})

        return requirement.second

    }

    override fun passwordValidation(password: String): Boolean {

        val specialCharacters: List<String> = listOf("!", "@", "#", "$", "%", "^", "&", "*")

        val requirements = listOf(
            Pair("Minimum 8 characters", password.length >= 8),
            Pair("At least one uppercase letter", password.any {char -> char.isUpperCase()}),
            Pair("At least one special character", password.any {char -> specialCharacters.contains(char.toString())})
        )

        return requirements.all { it.second }

    }

    override suspend fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun deleteUser(email: String) {
        firebaseAuth.currentUser?.delete()
    }

}