package com.example.sewakendaraan.room.userRoom

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepository(private val userDao: UserDao) {
    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData

    init {
        _readLoginData.value = null
    }
    fun setReadLoginData(user: User?){
        _readLoginData.value = user
    }
    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
    suspend fun userData(userId: Int): User?{
        return userDao.userData(userId)
    }
    suspend fun setLogin(username: String, password: String): User?{
        return userDao.login(username, password)
    }
}