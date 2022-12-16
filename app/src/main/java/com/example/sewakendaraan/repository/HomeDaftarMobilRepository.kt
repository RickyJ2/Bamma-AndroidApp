package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.data.User

class HomeDaftarMobilRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    private val _error = MutableLiveData<User>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg
    val error: LiveData<User>
        get() = _error

    private val _daftarMobil = MutableLiveData<List<DaftarMobil>>()
    val daftarMobil: LiveData<List<DaftarMobil>>
        get() = _daftarMobil

    init {
        resetVal()
        _daftarMobil.value = null
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
        _error.value = null
    }
    fun setCode(code: Int){
        _code.value = code
    }
    fun setMsg(msg: String){
        _msg.value = msg
    }
}