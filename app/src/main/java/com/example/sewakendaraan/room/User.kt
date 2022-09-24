package com.example.sewakendaraan.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val email: String,
    val username: String,
    val password: String,
    val dateofbirth: Date,
    val handphone: String

)