package com.example.sewakendaraan.room.kendaraanRoom

import androidx.room.*

@Dao
interface KendaraanDao {
    @Insert
    suspend fun addKendaraan(kendaraan: Kendaraan)

    @Update
    suspend fun updateKendaraan(kendaraan: Kendaraan)


    @Delete
    suspend fun deleteKendaraan(kendaraan: Kendaraan)


    @Query("SELECT * FROM kendaraan")
    suspend fun getKendaraan() : List<Kendaraan>

    @Query("SELECT * FROM kendaraan WHERE id =:kendaraan_id")
    suspend fun getKendaraan(kendaraan_id: Int) : Kendaraan
}