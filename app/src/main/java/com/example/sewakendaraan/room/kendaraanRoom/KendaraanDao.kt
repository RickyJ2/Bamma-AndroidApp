//package com.example.sewakendaraan.room.kendaraanRoom
//
//import androidx.room.*
//import com.example.sewakendaraan.data.DaftarMobil
//
//@Dao
//interface KendaraanDao {
//    @Insert
//    suspend fun addKendaraan(daftarMobil: DaftarMobil)
//
//    @Update
//    suspend fun updateKendaraan(daftarMobil: DaftarMobil)
//
//
//    @Delete
//    suspend fun deleteKendaraan(daftarMobil: DaftarMobil)
//
//
//    @Query("SELECT * FROM kendaraan")
//    suspend fun getKendaraan() : List<DaftarMobil>
//
//    @Query("SELECT * FROM kendaraan WHERE id =:kendaraan_id")
//    suspend fun getKendaraan(kendaraan_id: Int) : DaftarMobil
//}