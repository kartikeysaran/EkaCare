package com.yekitrak.ekacare.data.state

import com.yekitrak.ekacare.data.db.entity.UserEntity

sealed class UserUiState {
    object Loading : UserUiState()
    data class Success(val users: List<UserEntity>) : UserUiState()
    data class Error(val message: String) : UserUiState()
}