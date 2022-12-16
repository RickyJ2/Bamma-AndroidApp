package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.repository.RegisterRepository
import com.example.sewakendaraan.data.User

class RegisterViewModel(application: Application): AndroidViewModel(application) {
    private val repository: RegisterRepository = RegisterRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val error: LiveData<User>
        get() = repository.error

    val registerFormMutableLiveData = MutableLiveData<User>()
    val toogleBtnMutableLiveData = MutableLiveData<Boolean>()
    val progressBarMutableLiveData = MutableLiveData<Int>()
    val registerForm: LiveData<User>
        get() = registerFormMutableLiveData

    init{
        registerFormMutableLiveData.value = User(0,"","", "","","", "")
        toogleBtnMutableLiveData.value = false
        progressBarMutableLiveData.value = View.INVISIBLE
    }

    fun register(){
        if(!toogleBtnMutableLiveData.value!!){
            repository.setCode(0)
            repository.setMsg("You must check the agreement")
        }else{
            this.registerForm.value?.let { repository.register(it) }
        }
    }
    fun setDatePicker(date: String){
        registerFormMutableLiveData.value = User(
            0,
            registerForm.value!!.username,
            registerForm.value!!.email,
            registerForm.value!!.password,
            date,
            registerForm.value!!.handphone,
        "")
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}