package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.User
import com.example.sewakendaraan.repository.UserRepository
import okhttp3.MultipartBody

class ProfileUserViewModel (application: Application): AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val error: LiveData<User>
        get() = repository.error
    val readLoginData: LiveData<User>
        get() = repository.readLoginData

    val updateFormMutableLiveData = MutableLiveData<User>()
    val progressBarMutableLiveData = MutableLiveData<Int>()
    private val updateForm: LiveData<User>
        get() = updateFormMutableLiveData

    init{
        updateFormMutableLiveData.value = User(0,"","", "","","","")
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun updateUser(){
        updateForm.value?.let { repository.updateUser(it) }
    }
    fun userData(id: Int){
        repository.userData(id)
    }
    fun updateProfile(image: MultipartBody.Part){
        repository.updateProfile(image)
    }
    fun setupEditForm(){
        updateFormMutableLiveData.value = readLoginData.value
    }
    fun setDatePicker(date: String){
        updateFormMutableLiveData.value = User(
            updateForm.value!!.id,
            updateForm.value!!.username,
            updateForm.value!!.email,
            updateForm.value!!.password,
            date,
            updateForm.value!!.handphone,
            updateForm.value!!.image
        )
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}