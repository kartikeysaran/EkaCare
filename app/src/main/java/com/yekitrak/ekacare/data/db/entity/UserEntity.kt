package com.yekitrak.ekacare.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yekitrak.ekacare.data.constant.Constants

@Entity(tableName = Constants.USER_TABLE)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long=0,
    val name: String,
    val dob: String,
    val age: Int,
    val address: String,
    val city: String,
    val country: String
)