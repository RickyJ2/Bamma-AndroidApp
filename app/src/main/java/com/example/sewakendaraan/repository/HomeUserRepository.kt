package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataUser
import com.example.sewakendaraan.data.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeUserRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg

    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData
    init {
        _readLoginData.value = null
        resetVal()
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
    }
    fun userData(userId: Int){
        RClient.instances.userData(userId).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _readLoginData.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                _code.value = 0
            }
        })
    }
}