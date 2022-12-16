package com.example.sewakendaraan.api.response

import com.google.gson.annotations.SerializedName

class CreateResponse (
    @SerializedName("status") val stt:Int,
    @SerializedName("error") val e:Boolean,
    @SerializedName("message") val msg:String,
)