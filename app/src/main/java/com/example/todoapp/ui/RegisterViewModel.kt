package com.example.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> = _registrationResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registerUser(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {

        if (authRepository.emailValidation(email) || authRepository.passwordValidation(password)) {

            try {
                authRepository.registerUser(email, password)
                _registrationResult.postValue(true)
            } catch(e: Exception) {
                _registrationResult.postValue(false)
                _error.postValue(e.message)
            }
        } else {
            _error.postValue("Invalid email or password")
        }

    }


}