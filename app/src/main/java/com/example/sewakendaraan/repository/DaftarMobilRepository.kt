package com.example.sewakendaraan.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.CreateResponse
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.ResponseDaftarMobil
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarMobilRepository {
    private val _daftarMobil = MutableLiveData<List<Kendaraan>>()
    val daftarMobil: LiveData<List<Kendaraan>>
        get() = _daftarMobil

    init {
        _daftarMobil.value = null
    }
    fun showAllDaftarMobil(){
        RClient.instances.showAllDaftarMobil().enqueue(object:
            Callback<ResponseDaftarMobil> {
            override fun onResponse(call: Call<ResponseDaftarMobil>, response: Response<ResponseDaftarMobil>) {
                if(response.isSuccessful){
                    response.body().also { _daftarMobil.value = it?.data
                        Log.d("showAllDaftarMobil", it?.data.toString())
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDaftarMobil>, t: Throwable) {
                null.also { _daftarMobil.value = it }
                Log.d("showAllDaftarMobil",  t.toString())
            }
        })
    }
    fun showDaftarMobil(id: Int){
        RClient.instances.showDaftarMobil(id).enqueue(object:
            Callback<ResponseDaftarMobil> {
            override fun onResponse(call: Call<ResponseDaftarMobil>, response: Response<ResponseDaftarMobil>) {
                if(response.isSuccessful){
                    response.body().also { _daftarMobil.value = it?.data
                        Log.d("showDaftarMobil", it?.data.toString())
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDaftarMobil>, t: Throwable) {
                null.also { _daftarMobil.value = it }
                Log.d("showDaftarMobil",  t.toString())
            }
        })
    }
    fun addDaftarMobil(kendaraan: Kendaraan){
        RClient.instances.addDaftarMobil(kendaraan.namaMobil, kendaraan.alamat, kendaraan.harga).enqueue(
            object: Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if(response.isSuccessful){
                        Log.d("addDaftarMobil", response.body()?.msg.toString())
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Log.d("addDaftarMobil", jsonObj.getString("message"))
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    Log.d("addDaftarMobil",  t.toString())
                }
            })
    }
    fun updateDaftarMobil(kendaraan: Kendaraan){
        RClient.instances.updateDaftarMobil(kendaraan.id, kendaraan.namaMobil, kendaraan.alamat, kendaraan.harga).enqueue(
            object: Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if(response.isSuccessful){
                        Log.d("updateDaftarMobil", response.body()?.msg.toString())
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Log.d("updateDaftarMobil", jsonObj.getString("message"))
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    Log.d("updateDaftarMobil",  t.toString())
                }
            })
    }
    fun deleteDaftarMobil(id: Int){
        RClient.instances.deleteDaftarMobil(id).enqueue(object:
            Callback<CreateResponse> {
            override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                if(response.isSuccessful){
                    Log.d("deleteDaftarMobil", response.body()?.msg.toString())
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    Log.d("deleteDaftarMobil", jsonObj.getString("message"))
                }
            }
            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                null.also { _daftarMobil.value = it }
                Log.d("deleteDaftarMobil",  t.toString())
            }
        })
    }
}