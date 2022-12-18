package com.example.sewakendaraan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.api.response.ResponseDataRating
import com.example.sewakendaraan.data.Rating
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingRepository {
    private val _code = MutableLiveData<Int>()
    private val _msg = MutableLiveData<String>()
    val code: LiveData<Int>
        get() = _code
    val msg: LiveData<String>
        get() = _msg

    private val _rating = MutableLiveData<Rating>()
    val rating: LiveData<Rating>
        get() = _rating
    init {
        resetVal()
        _rating.value = Rating(0,0,0,0)
    }
    private fun resetVal(){
        _code.value = null
        _msg.value = ""
    }
    fun getRating(userId: Int, mobilId: Int){
        resetVal()
        RClient.instances.getRating(userId, mobilId).enqueue(object:
            Callback<ResponseDataRating> {
            override fun onResponse(call: Call<ResponseDataRating>, response: Response<ResponseDataRating>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _rating.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataRating>, t: Throwable) {
                _code.value = 0
            }
        })
    }
    fun addupdateRating(Rating: Rating){
        resetVal()
        RClient.instances.addupdateRating( Rating.id_user, Rating.id_mobil, Rating.rating).enqueue(
            object: Callback<ResponseDataRating> {
                override fun onResponse(call: Call<ResponseDataRating>, response: Response<ResponseDataRating>) {
                    if(response.isSuccessful){
                        _msg.value = response.body()?.msg.toString()
                        response.body().also { _rating.value = it?.data }
                        _code.value = response.code()
                    }else{
                        val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                        _code.value = response.code()
                        _msg.value = jsonObj.getString("message")
                    }
                }
                override fun onFailure(call: Call<ResponseDataRating>, t: Throwable) {
                    _code.value = 0
                }
            })
    }
    fun deleteRating(id: Int){
        resetVal()
        RClient.instances.deleteRating(id).enqueue(object:
            Callback<ResponseDataRating> {
            override fun onResponse(call: Call<ResponseDataRating>, response: Response<ResponseDataRating>) {
                if(response.isSuccessful){
                    _msg.value = response.body()?.msg.toString()
                    response.body().also { _rating.value = it?.data }
                    _code.value = response.code()
                }else{
                    val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                    _code.value = response.code()
                    _msg.value = jsonObj.getString("message")
                }
            }
            override fun onFailure(call: Call<ResponseDataRating>, t: Throwable) {
                _code.value = 0
            }
        })
    }
}