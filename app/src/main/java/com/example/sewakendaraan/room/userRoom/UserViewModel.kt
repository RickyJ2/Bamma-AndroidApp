package com.example.sewakendaraan.room.userRoom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    }
    fun setLogin(username: String, password: String){
        repository.setLogin(username, password)
    }
}