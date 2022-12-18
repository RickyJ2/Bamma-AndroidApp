package com.example.sewakendaraan.viewModel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.data.Rating
import com.example.sewakendaraan.repository.DaftarMobilRepository
import com.example.sewakendaraan.repository.RatingRepository

class KendaraanDetailViewModel(application: Application): AndroidViewModel(application) {
    private val repositoryDaftarMobil: DaftarMobilRepository = DaftarMobilRepository()
    private val repositoryRating: RatingRepository = RatingRepository()

    val codeDaftarMobil: LiveData<Int>
        get() = repositoryDaftarMobil.code
    val msgDaftarMobil: LiveData<String>
        get() = repositoryDaftarMobil.msg
    val daftarMobil: LiveData<DaftarMobil>
        get() = repositoryDaftarMobil.daftarMobil

    val codeRating: LiveData<Int>
        get() = repositoryRating.code
    val msgRating: LiveData<String>
        get() = repositoryRating.msg
    val rating: LiveData<Rating>
        get() = repositoryRating.rating

    val progressBarMutableLiveData = MutableLiveData<Int>()

    init {
        progressBarMutableLiveData.value = View.INVISIBLE
    }

    fun getRating(idUser: Int, idMobil: Int){
        repositoryRating.getRating(idUser, idMobil)
    }
    fun addupdateRating(rating: Rating){
        repositoryRating.addupdateRating(rating)
    }
    fun deleteRating(){
        if(rating.value != null){
            repositoryRating.deleteRating(rating.value!!.id)
        }
    }
    fun getDaftarMobilAt(id: Int){
        repositoryDaftarMobil.getDaftarMobilAt(id)
    }
    fun setProgressBar(isLoading: Int){
        progressBarMutableLiveData.value = isLoading
    }
}