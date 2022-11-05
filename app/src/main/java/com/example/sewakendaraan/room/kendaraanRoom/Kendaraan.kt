package com.example.sewakendaraan.room.kendaraanRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kendaraan (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val namaPemilik: String,
    val jenisKendaraan: String
)