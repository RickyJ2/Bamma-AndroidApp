package com.example.sewakendaraan.kendaraanRoom

import androidx.room.*

@Dao
interface KendaraanDao {
    @Insert
    suspend fun addKendaraan(kendaraan: Kendaraan)

    @Update
    suspend fun updateNote(kendaraan: Kendaraan)


    @Delete
    suspend fun deleteNote(kendaraan: Kendaraan)


    @Query("SELECT * FROM kendaraan")
    suspend fun getNotes() : List<Kendaraan>

    @Query("SELECT * FROM kendaraan WHERE id =:kendaraan_id")
    suspend fun getNote(kendaraan_id: Int) : List<Kendaraan>
}