package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.repository.LoginRepository
import com.example.sewakendaraan.data.User

class LoginViewModel (application: Application): AndroidViewModel(application) {
    private val repository: LoginRepository = LoginRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val error: LiveData<User>
        get() = repository.error
    val readLoginData: LiveData<User>
        get() = repository.readLoginData

    val loginFormMutableLiveData = MutableLiveData<User>()
    val progressBarMutableLiveData = MutableLiveData<Int>()

    val loginForm: LiveData<User>
        get() = loginFormMutableLiveData

    init {
        loginFormMutableLiveData.value = User(0, "", "", "", "", "","")
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun login(){
        this.repository.setLogin(loginFormMutableLiveData.value!!.username, loginFormMutableLiveData.value!!.password)
    }
    fun setUsernameForm(username: String){
        loginFormMutableLiveData.value = User(
            0,
            username,
            "",
            loginForm.value!!.password,
            "",
            "",
            "")
    }
    fun setPasswordForm(password: String){
        loginFormMutableLiveData.value = User(
            0,
            loginForm.value!!.username,
            "",
            password,
            "",
            "",
        "")
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}