package com.example.sewakendaraan.api.response

import com.example.sewakendaraan.data.Rating
import com.google.gson.annotations.SerializedName

data class ResponseDataRating(
    @SerializedName("status") val stt:String,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val msg:String,
    val totalData: Int,
    val data: Rating
)
