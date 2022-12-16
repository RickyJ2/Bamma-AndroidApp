package com.example.sewakendaraan.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.response.CreateResponse
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataUser
import com.example.sewakendaraan.data.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val _readLoginData = MutableLiveData<User>()
    private val _loadState = MutableLiveData<String>()
    val readLoginData: LiveData<User>
        get() = _readLoginData
    val loadState: LiveData<String>
        get() = _loadState

    init {
        _readLoginData.value = null
        _loadState.value = ""
    }
    fun setReadLoginData(user: User?){
        _readLoginData.value = user
    }
    fun setLoadState(state: String){
        _loadState.value = state
    }
    fun addUser(user: User){
        RClient.instances.register(user.username, user.email, user.password, user.dateOfBirth, user.handphone).enqueue(
            object: Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if(response.isSuccessful){
                        Log.d("addUser", response.body()?.msg.toString())

                        _loadState.value = "SUCCESS"
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Log.d("addUser", jsonObj.getString("message"))
                        _loadState.value = "FAILED"
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    Log.d("addUser",  t.toString())
                    _loadState.value = "FAILED"
                }
            })
    }
    fun updateUser(user: User){
        RClient.instances.updateUser(user.id, user.username, user.email, user.password, user.dateOfBirth, user.handphone).enqueue(
            object: Callback<ResponseDataUser> {
                override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                    if(response.isSuccessful){
                        response.body().also { _readLoginData.value = it?.data
                            Log.d("updateUser", it?.data.toString())
                        }
                    }
                }
                override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                    null.also { _readLoginData.value = it }
                    Log.d("updateUser",  t.toString())
                }
        })
    }
    fun userData(userId: Int){
        RClient.instances.userData(userId).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    response.body().also { _readLoginData.value = it?.data
                        Log.d("userData", it?.data.toString())
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                null.also { _readLoginData.value = it }
                Log.d("userData",  t.toString())
            }
        })
    }
    fun setLogin(username: String, password: String){
        RClient.instances.login(username, password).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    response.body().also { _readLoginData.value = it?.data
                        _loadState.value = "SUCCESS"
                    }
                }else{
                    _loadState.value = "FAILED"
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                null.also { _readLoginData.value = it }
                Log.d("Login", t.toString())
                _loadState.value = "FAILED"
            }
        })
    }
}