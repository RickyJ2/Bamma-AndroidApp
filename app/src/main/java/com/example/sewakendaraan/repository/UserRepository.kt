package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.CreateResponse
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.ResponseDataUser
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDao
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val userDao: UserDao) {
    private val _readLoginData = MutableLiveData<User>()
    val readLoginData: LiveData<User>
        get() = _readLoginData

    init {
        _readLoginData.value = null
    }
    fun setReadLoginData(user: User?){
        _readLoginData.value = user
    }
    fun addUser(user: User): String{
        //userDao.addUser(user)
        var msg = ""
        RClient.instances.addUser(user.username, user.email, user.password, user.dateofbirth, user.handphone).enqueue(
            object: Callback<CreateResponse> {
                override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                    msg = if(response.isSuccessful){
                        "${response.body()?.msg}"
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        jsonObj.getString("message")
                    }
                }

                override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                    msg = "Failure"
                }
            })
        return msg
    }
    fun updateUser(user: User): String{
        //userDao.updateUser(user)
        var msg = ""
        RClient.instances.updateUser(user.id, user.username, user.email, user.password, user.dateofbirth, user.handphone).enqueue(
            object: Callback<CreateResponse> {
            override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                msg = if(response.isSuccessful){
                    "${response.body()?.msg}"
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    jsonObj.getString("message")
                }
            }

            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                msg = "Failure"
            }
        })
        return msg
    }
    fun userData(userId: Int): User?{
        //return userDao.userData(userId)
        var user: User? = null
        RClient.instances.userData(userId).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    response.body().also { user = it?.data
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                null.also { user = it }
            }
        })
        return user
    }
    fun setLogin(username: String, password: String): User?{
        //return userDao.login(username, password)
        var user: User? = null
        RClient.instances.login(username, password).enqueue(object:
            Callback<ResponseDataUser> {
            override fun onResponse(call: Call<ResponseDataUser>, response: Response<ResponseDataUser>) {
                if(response.isSuccessful){
                    response.body().also { user = it?.data
                    }
                }
            }
            override fun onFailure(call: Call<ResponseDataUser>, t: Throwable) {
                null.also { user = it }
            }
        })
        return user
    }
}