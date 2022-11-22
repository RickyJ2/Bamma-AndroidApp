package com.example.sewakendaraan.api


import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import com.google.gson.annotations.SerializedName

data class ResponseDaftarMobil(
    @SerializedName("status") val stt:String,
    val totalData: Int,
    val data: List<Kendaraan>
)
