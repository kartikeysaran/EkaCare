package com.yekitrak.ekacare.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yekitrak.ekacare.data.db.UserUseCase
import com.yekitrak.ekacare.data.db.entity.UserEntity
import com.yekitrak.ekacare.data.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            userUseCase.getAllUserDetails()
                .catch { e -> _uiState.value = UserUiState.Error(e.message ?: "Unknown Error") }
                .collect { users -> _uiState.value = UserUiState.Success(users) }

        }
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            withContext(Dispatchers.IO) {
                userUseCase.addUser(user)
            }
            loadUsers()
        }
    }

    fun deleteUser(id: Long) {
        viewModelScope.launch {
            try {
                _uiState.value = UserUiState.Loading
                withContext(Dispatchers.IO) {
                    userUseCase.deleteUser(id)
                }
                loadUsers()
            } catch (e: Exception) {
                //Toast.makeText(, e.message, Toast.LENGTH_SHORT)
                Log.e("DB", e.message.toString())
            }
        }
    }
}