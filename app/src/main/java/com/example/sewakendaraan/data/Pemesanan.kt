package com.example.sewakendaraan.data

import androidx.room.PrimaryKey

data class Pemesanan(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val id_mobil: Int,
    val id_user: Int,
    var tgl_pengembalian: String,
    var tgl_peminjaman: String,
    val status: Int,
)
