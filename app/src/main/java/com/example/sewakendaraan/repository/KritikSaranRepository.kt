package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataKritikSaran
import com.example.sewakendaraan.data.KritikSaran
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KritikSaranRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    private val _error = MutableLiveData<KritikSaran>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg
    val error: LiveData<KritikSaran>
        get() = _error

    private val _kritikSaranList = MutableLiveData<List<KritikSaran>>()
    val kritikSaranList: LiveData<List<KritikSaran>>
        get() = _kritikSaranList
    init {
        resetVal()
        _kritikSaranList.value = null
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
        _error.value = null
    }
    fun getKritikSaran(userId: Int){
        RClient.instances.getKritikSaran(userId).enqueue(object:
            Callback<ResponseDataKritikSaran> {
            override fun onResponse(call: Call<ResponseDataKritikSaran>, response: Response<ResponseDataKritikSaran>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _kritikSaranList.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataKritikSaran>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun addKritikSaran(kritikSaran: KritikSaran){
        resetVal()
        RClient.instances.addKritikSaran( kritikSaran.id_user, kritikSaran.content).enqueue(
            object: Callback<ResponseDataKritikSaran> {
                override fun onResponse(call: Call<ResponseDataKritikSaran>, response: Response<ResponseDataKritikSaran>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _kritikSaranList.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        if(_code.value == 400 && jsonObj.has("error")){
                            val jsonError = jsonObj.getJSONObject("error")
                            _error.value = KritikSaran(
                                0,
                                0,
                                if(jsonError.has("content")) jsonError.getJSONArray("content")[0].toString() else "",
                            )
                        }else {
                            _msg.value = jsonObj.getString("message")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataKritikSaran>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
    fun updateKritikSaran(kritikSaran: KritikSaran){
        resetVal()
        RClient.instances.updateKritikSaran(kritikSaran.id, kritikSaran.id_user, kritikSaran.content).enqueue(
            object: Callback<ResponseDataKritikSaran> {
                override fun onResponse(call: Call<ResponseDataKritikSaran>, response: Response<ResponseDataKritikSaran>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _kritikSaranList.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        if(_code.value == 400 && jsonObj.has("error")){
                            val jsonError = jsonObj.getJSONObject("error")
                            _error.value = KritikSaran(
                                0,
                                0,
                                if(jsonError.has("content")) jsonError.getJSONArray("content")[0].toString() else "",
                            )
                        }else {
                            _msg.value = jsonObj.getString("message")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataKritikSaran>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
    fun deleteKritiKSaran(id: Int){
        RClient.instances.deleteKritiKSaran(id).enqueue(object:
            Callback<ResponseDataKritikSaran> {
            override fun onResponse(call: Call<ResponseDataKritikSaran>, response: Response<ResponseDataKritikSaran>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _kritikSaranList.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataKritikSaran>, t: Throwable) {
                _code.value = 0
            }
        })
    }
}