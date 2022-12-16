package com.example.sewakendaraan.data

import androidx.room.PrimaryKey

data class Cabang(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nama: String,
    val vicinity: String,
    val lat: Double,
    val lng: Double,
)
