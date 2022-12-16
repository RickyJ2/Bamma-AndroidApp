package com.example.sewakendaraan.data

import androidx.room.PrimaryKey

data class Rating(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val id_mobil: Int,
    val id_user: Int,
    var rating: Int,
)
