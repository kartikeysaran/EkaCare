package com.yekitrak.ekacare.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yekitrak.ekacare.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserAll(users: List<UserEntity>): List<Long>

    @Query("select * from usertable where id Like :id")
    fun getUserDataDetails(id:Long): UserEntity

    @Query("DELETE FROM USERTABLE")
    fun deleteAll()

    @Query("SELECT * FROM usertable")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("DELETE FROM USERTABLE WHERE id=:id")
    fun deleteUser(id:Long)
}