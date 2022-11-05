package com.example.sewakendaraan.room.userRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val username: String,
    val password: String,
    val dateofbirth: String,
    val handphone: String
)