package com.example.sewakendaraan.room.userRoom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val readLoginData: LiveData<User>
        get() = repository.readLoginData

    init{
        val userDao = UserDB.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
        setUserData()
    }
    fun setUserData(){
        viewModelScope.launch(Dispatchers.Main){
            setReadLoginData(readLoginData.value?.let { repository.userData(it.id) })
        }
    }
    fun setLogin(username: String, password: String) {
        runBlocking {
            setReadLoginData(repository.setLogin(username, password))
        }
    }
    private fun setReadLoginData(user: User?){
        repository.setReadLoginData(user)
    }
}