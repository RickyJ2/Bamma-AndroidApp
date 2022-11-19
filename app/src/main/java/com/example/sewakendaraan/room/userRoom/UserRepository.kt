package com.example.sewakendaraan.room.userRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepository(private val userDao: UserDao) {
    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData

    init {
        _readLoginData.value = null
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }
    fun setLogin(username: String, password: String){
        _readLoginData.value = userDao.login(username, password)?.value
    }
}