package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.repository.HomeUserRepository
import com.example.sewakendaraan.data.User

class HomeViewModel (application: Application): AndroidViewModel(application) {
    private val repositoryUser: HomeUserRepository = HomeUserRepository()
    val codeUser: LiveData<Int>
        get() = repositoryUser.code
    val msgUser: LiveData<String>
        get() = repositoryUser.msg
    val readLoginData: LiveData<User>
        get() = repositoryUser.readLoginData

    val progressBarMutableLiveData = MutableLiveData<Int>()

    init {
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun userData(id: Int){
        repositoryUser.userData(id)
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}