package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.Cabang
import com.example.sewakendaraan.repository.CabangRepository

class TentangKamiViewModel(application: Application): AndroidViewModel(application)  {
    private val repository: CabangRepository = CabangRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val cabangList: LiveData<List<Cabang>>
        get() = repository.cabangList
    val progressBarMutableLiveData = MutableLiveData<Int>()

    init{
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun getCabang(){
        repository.getCabang()
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}