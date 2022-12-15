package com.example.sewakendaraan.api

import com.example.sewakendaraan.room.userRoom.User
import com.google.gson.annotations.SerializedName

data class ResponseDataUser(
    @SerializedName("status") val stt:String,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val msg:String,
    val totalData: Int,
    val data: User
)
