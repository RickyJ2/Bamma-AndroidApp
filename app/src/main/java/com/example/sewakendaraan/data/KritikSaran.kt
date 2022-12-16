package com.example.sewakendaraan.data

import androidx.room.PrimaryKey

data class KritikSaran(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val id_user: Int,
    var content: String,
)
