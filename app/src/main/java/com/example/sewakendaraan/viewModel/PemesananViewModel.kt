package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.data.Pemesanan
import com.example.sewakendaraan.repository.DaftarMobilRepository
import com.example.sewakendaraan.repository.PemesananRepository

class PemesananViewModel (application: Application): AndroidViewModel(application) {
    private val repositoryDaftarMobil: DaftarMobilRepository = DaftarMobilRepository()
    private val repositoryPemesanan: PemesananRepository = PemesananRepository()

    val codeDaftarMobil: LiveData<Int>
        get() = repositoryDaftarMobil.code
    val msgDaftarMobil: LiveData<String>
        get() = repositoryDaftarMobil.msg
    val daftarMobil: LiveData<DaftarMobil>
        get() = repositoryDaftarMobil.daftarMobil

    val codePemesanan: LiveData<Int>
        get() = repositoryPemesanan.code
    val msgPemesanan: LiveData<String>
        get() = repositoryPemesanan.msg
    val errorPemesanan: LiveData<Pemesanan>
        get() = repositoryPemesanan.error
    val pemesananFormMutableLiveData = MutableLiveData<Pemesanan>()
    val progressBarMutableLiveData = MutableLiveData<Int>()
    val cardSelectedMutableLiveData = MutableLiveData<Int>()
    val pickCarMutableLiveData = MutableLiveData<Int>()
    val pemesananForm: LiveData<Pemesanan>
        get() = pemesananFormMutableLiveData

    init{
        pemesananFormMutableLiveData.value = Pemesanan(0,0,0, "","",0)
        progressBarMutableLiveData.value = View.INVISIBLE
        cardSelectedMutableLiveData.value = View.INVISIBLE
        pickCarMutableLiveData.value = View.VISIBLE
    }
    fun addPemesanan(id_mobil: Int, id_user: Int){
        repositoryPemesanan.addPemesanan(
            Pemesanan(
            0,
            id_mobil,
            id_user,
            pemesananForm.value!!.durasi,
            pemesananForm.value!!.tgl_peminjaman,
            0
        )
        )
    }
    fun getDaftarMobilAt(id: Int){
        repositoryDaftarMobil.getDaftarMobilAt(id)
    }
    fun setTimePicker(time: String){
        pemesananFormMutableLiveData.value = Pemesanan(
            0,
            0,
            0,
            time,
            pemesananForm.value!!.tgl_peminjaman,
            0
        )
    }
    fun setDatePicker(date: String){
        pemesananFormMutableLiveData.value = Pemesanan(
            0,
            0,
            0,
            pemesananForm.value!!.durasi,
            date,
            0
        )
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
    fun setCardSelected(isView: Int){
        if(isView == View.VISIBLE){
            cardSelectedMutableLiveData.value = isView
            pickCarMutableLiveData.value = View.INVISIBLE
        }else{
            cardSelectedMutableLiveData.value = isView
            pickCarMutableLiveData.value = View.VISIBLE
        }
    }
}