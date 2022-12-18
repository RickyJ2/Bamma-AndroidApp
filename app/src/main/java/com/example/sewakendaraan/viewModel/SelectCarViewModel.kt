package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.repository.DaftarMobilRepository

class SelectCarViewModel (application: Application): AndroidViewModel(application) {
    private val repository: DaftarMobilRepository = DaftarMobilRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val daftarMobilList: LiveData<List<DaftarMobil>>
        get() = repository.daftarMobilList
    val progressBarMutableLiveData = MutableLiveData<Int>()
    init {
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun getDaftarMobil(){
        repository.getDaftarMobil()
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}