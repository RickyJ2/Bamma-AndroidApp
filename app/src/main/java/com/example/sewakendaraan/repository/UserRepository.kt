package com.example.sewakendaraan.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.CreateResponse
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.ResponseDataUser
import com.example.sewakendaraan.room.userRoom.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData

    init {
        _readLoginData.value = null
    }
    fun addUser(user: User){
        //userDao.addUser(user)
        RClient.instances.addUser(user.username, user.email, user.password, user.dateOfBirth, user.handphone).enqueue(
            object: Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    if(response.isSuccessful){
                        Log.d("addUser", response.body()?.msg.toString())
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        Log.d("addUser", jsonObj.getString("message"))
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    Log.d("addUser",  t.toString())
                }
            })
    }
    fun updateUser(user: User){
        //userDao.updateUser(user)
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
        //return userDao.userData(userId)
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
        //return userDao.login(username, password)
        RClient.instances.login(username, password).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    response.body().also { _readLoginData.value = it?.data
                        Log.d("setLogin", it?.data.toString())
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                null.also { _readLoginData.value = it }
                Log.d("setLogin", t.toString())
            }
        })
    }
}