package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataCabang
import com.example.sewakendaraan.data.Cabang
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CabangRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg

    private val _cabangList = MutableLiveData<List<Cabang>>()
    val cabangList: LiveData<List<Cabang>>
        get() = _cabangList
    private val _cabang = MutableLiveData<Cabang>()
    val cabang: LiveData<Cabang>
        get() = _cabang

    init {
        resetVal()
        _cabangList.value = null
        _cabang.value = null
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
    }
    fun getCabang(){
        RClient.instances.getCabang().enqueue(object:
            Callback<ResponseDataCabang> {
            override fun onResponse(call: Call<ResponseDataCabang>, response: Response<ResponseDataCabang>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _cabangList.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataCabang>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun getCabangAt(id: Int){
        RClient.instances.getCabangAt(id).enqueue(object:
            Callback<ResponseDataCabang> {
            override fun onResponse(call: Call<ResponseDataCabang>, response: Response<ResponseDataCabang>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _cabang.value = it!!.data[0] }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataCabang>, t: Throwable) {
                _code.value = 0
            }
        })
    }
}