package com.example.sewakendaraan.room.userRoom

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
    var handphone: String
)