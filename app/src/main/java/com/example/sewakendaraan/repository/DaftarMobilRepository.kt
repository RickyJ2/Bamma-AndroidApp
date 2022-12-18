package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataDaftarMobil
import com.example.sewakendaraan.api.response.ResponseDataMobil
import com.example.sewakendaraan.data.DaftarMobil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarMobilRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg

    private val _daftarMobilList = MutableLiveData<List<DaftarMobil>>()
    val daftarMobilList: LiveData<List<DaftarMobil>>
        get() = _daftarMobilList
    private val _daftarMobil = MutableLiveData<DaftarMobil>()
    val daftarMobil: LiveData<DaftarMobil>
        get() = _daftarMobil

    init {
        resetVal()
        _daftarMobilList.value = null
        _daftarMobil.value = null
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
    }

    fun getDaftarMobil(){
        resetVal()
        RClient.instances.getDaftarMobil().enqueue(object:
            Callback<ResponseDataDaftarMobil> {
            override fun onResponse(call: Call<ResponseDataDaftarMobil>, response: Response<ResponseDataDaftarMobil>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _daftarMobilList.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataDaftarMobil>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun getDaftarMobilAt(id: Int){
        resetVal()
        RClient.instances.getDaftarMobilAt(id).enqueue(object:
            Callback<ResponseDataMobil> {
            override fun onResponse(call: Call<ResponseDataMobil>, response: Response<ResponseDataMobil>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _daftarMobil.value = it!!.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataMobil>, t: Throwable) {
                _code.value = 0
            }
        })
    }
}