package com.example.sewakendaraan.room.userRoom

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Query("SELECT * FROM user WHERE id =:idValue")
    fun userData(idValue: Int): LiveData<User>?
    @Query("SELECT * FROM user WHERE username =:usernameValue AND password =:passwordValue")
    suspend fun ceklogin(usernameValue: String, passwordValue: String): User?
}