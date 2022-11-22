package com.example.sewakendaraan.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sewakendaraan.repository.UserRepository
import com.example.sewakendaraan.room.userRoom.User

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository()
    val readLoginData: LiveData<User>
        get() = repository.readLoginData

    /*init{
        //val userDao = UserDB.getDatabase(application).userDao()
    }*/
    fun addUser(user: User){
        /*viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }*/
        repository.addUser(user)
    }
    fun updateUser(user: User){
        /*runBlocking {
            repository.updateUser(user)
        }*/
        repository.updateUser(user)
        Log.d("VM", readLoginData.value.toString())
    }
    fun setUserData(id: Int){
        repository.userData(id)
        Log.d("VM", readLoginData.value.toString())

    }
    fun setLogin(username: String, password: String) {
        repository.setLogin(username, password)
        Log.d("VM", readLoginData.value.toString())

    }
    private fun setReadLoginData(user: User?){
        repository.setReadLoginData(user)
    }
}