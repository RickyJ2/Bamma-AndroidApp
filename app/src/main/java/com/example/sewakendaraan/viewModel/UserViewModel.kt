package com.example.sewakendaraan.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sewakendaraan.repository.UserRepository
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDB
import kotlinx.coroutines.runBlocking

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository
    val readLoginData: LiveData<User>
        get() = repository.readLoginData

    init{
        val userDao = UserDB.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }
    fun addUser(user: User): String{
        /*viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }*/
        return repository.addUser(user)
    }
    fun updateUser(user: User): String{
        /*runBlocking {
            repository.updateUser(user)
        }*/
        val msg  = repository.updateUser(user)
        setUserData(readLoginData.value!!.id)
        return msg
    }
    fun setUserData(id: Int){
        runBlocking {
            setReadLoginData(repository.userData(id))
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