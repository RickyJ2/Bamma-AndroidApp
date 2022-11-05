package com.example.sewakendaraan.room.userRoom

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("SELECT * FROM user WHERE id =:user_id")
    suspend fun getUser(user_id: Int) : User
    @Query("SELECT * FROM user WHERE username =:usernameValue AND password =:passwordValue")
    suspend fun getUsernamePassword(usernameValue: String, passwordValue: String): User?
}