package com.example.sewakendaraan.room.userRoom

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Query("SELECT * FROM user WHERE id =:idValue")
    suspend fun userData(idValue: Int): User?
    @Query("SELECT * FROM user WHERE username =:usernameValue AND password =:passwordValue")
    suspend fun login(usernameValue: String, passwordValue: String): User?
}