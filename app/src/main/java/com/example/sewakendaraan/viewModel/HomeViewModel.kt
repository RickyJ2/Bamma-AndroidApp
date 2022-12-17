package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.data.User
import com.example.sewakendaraan.repository.DaftarMobilRepository
import com.example.sewakendaraan.repository.UserRepository

class HomeViewModel (application: Application): AndroidViewModel(application) {
    private val repositoryUser: UserRepository = UserRepository()
    private val repositoryDaftarMobil: DaftarMobilRepository = DaftarMobilRepository()
    val codeUser: LiveData<Int>
        get() = repositoryUser.code
    val msgUser: LiveData<String>
        get() = repositoryUser.msg
    val readLoginData: LiveData<User>
        get() = repositoryUser.readLoginData

    val codeDaftarMobil: LiveData<Int>
        get() = repositoryDaftarMobil.code
    val msgDaftarMobil: LiveData<String>
        get() = repositoryDaftarMobil.msg
    val daftarMobil: LiveData<List<DaftarMobil>>
        get() = repositoryDaftarMobil.daftarMobilList

    val progressBarMutableLiveData = MutableLiveData<Int>()

    init {
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun userData(id: Int){
        repositoryUser.userData(id)
    }
    fun getDaftarMobil(){
        repositoryDaftarMobil.getDaftarMobil()
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}