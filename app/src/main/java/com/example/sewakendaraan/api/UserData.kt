package com.example.sewakendaraan.api

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id") val id:Int,
    @SerializedName("username") val username:String,
    @SerializedName("email") val email:String,
    @SerializedName("password") val password:String,
    @SerializedName("dateofbirth") val dateofbirth:String,
    @SerializedName("handphone") val handphone:String
)
