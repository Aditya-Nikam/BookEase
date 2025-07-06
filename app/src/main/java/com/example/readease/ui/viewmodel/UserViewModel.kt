package com.example.readease.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.readease.data.model.User
import com.example.readease.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loggedInUser = MutableLiveData<User?>()
    val loggedInUser: LiveData<User?> get() = _loggedInUser

    fun register(user: User) = viewModelScope.launch {
        try {
            repository.register(user)
        } catch (e: Exception) {
            // handle duplicate email or other DB exceptions
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loggedInUser.value = repository.login(email, password)
    }

    fun getUserByEmail(email: String) = liveData {
        emit(repository.getUserByEmail(email))
    }
}
