package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.repository.ReqResetPasswordRepository
import com.example.sewakendaraan.room.userRoom.User

class ReqResetPasswordViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ReqResetPasswordRepository = ReqResetPasswordRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val error: LiveData<User>
        get() = repository.error

    val usernameFormMutableLiveData = MutableLiveData<String>()
    val progressBarMutableLiveData = MutableLiveData<Int>()

    private val usernameForm: LiveData<String>
        get() = usernameFormMutableLiveData

    init {
        usernameFormMutableLiveData.value = ""
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun reqResetPassword(){
        repository.reqResetPassword(usernameForm.value.toString())
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}