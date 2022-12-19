package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataPemesanan
import com.example.sewakendaraan.data.Pemesanan
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PemesananRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    private val _error = MutableLiveData<Pemesanan>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg
    val error: LiveData<Pemesanan>
        get() = _error

    private val _pemesananList = MutableLiveData<List<Pemesanan>>()
    val pemesananList: LiveData<List<Pemesanan>>
        get() = _pemesananList
    init {
        resetVal()
        _pemesananList.value = null
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
        _error.value = null
    }
    fun getPemesanan(userId: Int){
        RClient.instances.getPemesanan(userId).enqueue(object:
            Callback<ResponseDataPemesanan> {
            override fun onResponse(call: Call<ResponseDataPemesanan>, response: Response<ResponseDataPemesanan>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _pemesananList.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataPemesanan>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun addPemesanan(pemesanan: Pemesanan){
        resetVal()
        RClient.instances.addPemesanan(pemesanan.id_mobil, pemesanan.id_user, pemesanan.tgl_pengembalian, pemesanan.tgl_peminjaman).enqueue(
            object: Callback<ResponseDataPemesanan> {
                override fun onResponse(call: Call<ResponseDataPemesanan>, response: Response<ResponseDataPemesanan>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _pemesananList.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        if(_code.value == 400 && jsonObj.has("error")){
                            val jsonError = jsonObj.getJSONObject("error")
                            _error.value = Pemesanan(
                                0,
                                0,
                                0,
                                if(jsonError.has("tgl_pengembalian")) jsonError.getJSONArray("tgl_pengembalian")[0].toString() else "",
                                if(jsonError.has("tgl_peminjaman")) jsonError.getJSONArray("tgl_peminjaman")[0].toString() else "",
                                0
                            )
                        }else {
                            _msg.value = jsonObj.getString("message")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataPemesanan>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
    fun updatePemesanan(pemesanan: Pemesanan){
        resetVal()
        RClient.instances.updatePemesanan(pemesanan.id, pemesanan.id_mobil, pemesanan.id_user, pemesanan.tgl_pengembalian, pemesanan.tgl_peminjaman).enqueue(
            object: Callback<ResponseDataPemesanan> {
                override fun onResponse(call: Call<ResponseDataPemesanan>, response: Response<ResponseDataPemesanan>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _pemesananList.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        if(_code.value == 400 && jsonObj.has("error")){
                            val jsonError = jsonObj.getJSONObject("error")
                            _error.value = Pemesanan(
                                0,
                                0,
                                0,
                                if(jsonError.has("tgl_pengembalian")) jsonError.getJSONArray("tgl_pengembalian")[0].toString() else "",
                                if(jsonError.has("tgl_peminjaman")) jsonError.getJSONArray("tgl_peminjaman")[0].toString() else "",
                                0
                            )
                        }else {
                            _msg.value = jsonObj.getString("message")
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataPemesanan>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
}