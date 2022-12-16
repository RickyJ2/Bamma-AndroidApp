package com.example.sewakendaraan.data

import androidx.room.PrimaryKey

data class Pemesanan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val id_mobil: Int,
    val id_user: Int,
    var durasi: String,
    var tgl_peminjaman: String,
    val status: Int,
)
