package com.example.sewakendaraan.room.userRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val dateOfBirth: String,
    val handphone: String
)