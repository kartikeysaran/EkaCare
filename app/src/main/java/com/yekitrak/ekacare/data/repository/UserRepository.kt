package com.yekitrak.ekacare.data.repository

import com.yekitrak.ekacare.data.db.entity.UserEntity
import com.yekitrak.ekacare.data.db.dao.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UserRepository {

    fun addUser(users: UserEntity):Long

    fun addUserList(users: List<UserEntity>):List<Long>

    fun deleteUser(id: Long)

    fun getAllUserData(): Flow<List<UserEntity>>

    fun getUserDataDetails(id:Long): UserEntity

}

class UserRepositoryImpl @Inject constructor(
    private  var usersDao: UserDao
):UserRepository{

    override fun addUser(users: UserEntity): Long {
        return usersDao.insertUser(users)
    }

    override fun addUserList(users:List<UserEntity>): List<Long> {
        return usersDao.insertUserAll(users)
    }

    override fun deleteUser(id: Long) {
        usersDao.deleteUser(id)
    }

    override fun getUserDataDetails(id:Long): UserEntity {
        return usersDao.getUserDataDetails(id)
    }

    override fun getAllUserData(): Flow<List<UserEntity>> {
        return usersDao.getAllUsers()
    }
}