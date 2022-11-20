package com.example.sewakendaraan.room.userRoom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private var userTemp: User? = null
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
        viewModelScope.launch(Dispatchers.IO) {
            userTemp = readLoginData.value?.let { repository.userData(it.id) }
        }
        setReadLoginData()
    }
    fun setLogin(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userTemp = repository.setLogin(username, password)
            Log.d("Login123", "Text " + userTemp?.username.toString())
        }
        Log.d("Login123", "Text2 " + userTemp?.username.toString())
        setReadLoginData()
    }
    private fun setReadLoginData(){
        repository.setReadLoginData(userTemp)
    }
}