package com.example.sewakendaraan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DaftarMobil (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val id_cabang: Int,
    val model: String,
    val tipe: String,
    val kapasitas: Int,
    val harga: Int,
    val deskripsi: String,
    val image: String,
)