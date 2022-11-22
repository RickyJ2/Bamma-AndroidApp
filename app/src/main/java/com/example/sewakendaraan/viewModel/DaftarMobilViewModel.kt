package com.example.sewakendaraan.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.sewakendaraan.repository.DaftarMobilRepository
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan

class DaftarMobilViewModel(application: Application): AndroidViewModel(application) {
    private val repository: DaftarMobilRepository = DaftarMobilRepository()
    val daftarMobil: LiveData<List<Kendaraan>>
        get() = repository.daftarMobil

    fun showAllDaftarMobil(){
        repository.showAllDaftarMobil()
    }
    fun showDaftarMobil(id: Int){
        repository.showDaftarMobil(id)
    }
    fun addDaftarMobil(kendaraan: Kendaraan){
        repository.addDaftarMobil(kendaraan)
    }
    fun updateDaftarMobil(kendaraan: Kendaraan){
        repository.updateDaftarMobil(kendaraan)
    }
    fun deleteDaftarMobil(id: Int){
        repository.deleteDaftarMobil(id)
    }
}