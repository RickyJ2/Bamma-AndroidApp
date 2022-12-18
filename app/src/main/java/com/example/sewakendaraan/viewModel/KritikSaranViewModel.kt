package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.KritikSaran
import com.example.sewakendaraan.repository.KritikSaranRepository

class KritikSaranViewModel (application: Application): AndroidViewModel(application) {
    private val repository: KritikSaranRepository = KritikSaranRepository()
    val code: LiveData<Int>
        get() = repository.code
    val msg: LiveData<String>
        get() = repository.msg
    val error: LiveData<KritikSaran>
        get() = repository.error
    val kritikSaranList: LiveData<List<KritikSaran>>
        get() = repository.kritikSaranList

    val kritikSaranFormMutableLiveData = MutableLiveData<KritikSaran>()
    val progressBarMutableLiveData = MutableLiveData<Int>()
    private val kritikSaranForm: LiveData<KritikSaran>
        get() = kritikSaranFormMutableLiveData

    init {
        kritikSaranFormMutableLiveData.value = KritikSaran(0, 0, "")
        progressBarMutableLiveData.value = View.INVISIBLE
    }
    fun getKritikSaran(userId: Int){
        repository.getKritikSaran(userId)
    }
    fun addKritikSaran(){
        this.kritikSaranForm.value?.let { repository.addKritikSaran(it) }
    }
    fun updateKritikSaran(){
        this.kritikSaranForm.value?.let { repository.updateKritikSaran(it) }
    }
    fun deleteKritikSaran(id: Int){
        repository.deleteKritiKSaran(id)
    }
    fun setIdUser(id: Int){
        kritikSaranFormMutableLiveData.value = KritikSaran(
            kritikSaranForm.value!!.id,
            id,
            kritikSaranForm.value!!.content
        )
    }
    fun setupEditForm(id: Int){
        kritikSaranFormMutableLiveData.value = kritikSaranList.value?.get(id)
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}