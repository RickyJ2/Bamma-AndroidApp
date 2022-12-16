package com.example.sewakendaraan.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sewakendaraan.repository.UserRepository
import com.example.sewakendaraan.data.User

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository()
    val readLoginData: LiveData<User>
        get() = repository.readLoginData
    val loadState: LiveData<String>
        get() = repository.loadState

    fun addUser(user: User){
        repository.setLoadState("LOADING")
        repository.addUser(user)
    }
    fun updateUser(user: User){
        repository.updateUser(user)
    }
    fun setUserData(id: Int){
        repository.userData(id)
    }
    fun setLogin(username: String, password: String) {
        repository.setLoadState("LOADING")
        repository.setLogin(username, password)
    }
    private fun setReadLoginData(user: User?){
        repository.setReadLoginData(user)
    }
}