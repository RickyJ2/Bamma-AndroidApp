package com.example.sewakendaraan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var username: String,
    var email: String,
    var password: String,
    var dateOfBirth: String,
    var handphone: String,
    var image: String
)