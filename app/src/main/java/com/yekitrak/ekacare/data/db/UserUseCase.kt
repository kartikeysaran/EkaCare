package com.yekitrak.ekacare.data.db

import com.yekitrak.ekacare.data.db.entity.UserEntity
import com.yekitrak.ekacare.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserUseCase {
    suspend fun addUser(users: UserEntity): Long
    suspend fun addUserList(users: List<UserEntity>): List<Long>
    suspend fun deleteUser(id: Long)
    suspend fun getAllUserDetails(): Flow<List<UserEntity>>
    suspend fun getUserDetails(id: Long): UserEntity
}

class UserUseCaseImpl @Inject constructor(private var userRepository: UserRepository):UserUseCase{
    override suspend fun addUser(users: UserEntity): Long {
        val id= userRepository.addUser(users)
        return id
    }

    override suspend fun addUserList(users: List<UserEntity>): List<Long> {
        val id= userRepository.addUserList(users)
        return id
    }

    override suspend fun deleteUser(id: Long) {
        userRepository.deleteUser(id)
    }

    override suspend fun getAllUserDetails(): Flow<List<UserEntity>> {
        return userRepository.getAllUserData()
    }

    override suspend fun getUserDetails(id: Long): UserEntity {
        return userRepository.getUserDataDetails(id)
    }


}