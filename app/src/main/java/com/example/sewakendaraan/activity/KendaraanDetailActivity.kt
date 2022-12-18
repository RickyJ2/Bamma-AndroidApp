package com.example.sewakendaraan.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.sewakendaraan.R
import com.example.sewakendaraan.activity.pemesanan.PemesananActivity
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.data.Rating
import com.example.sewakendaraan.databinding.ActivityKendaraanDetailBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.viewModel.KendaraanDetailViewModel

class KendaraanDetailActivity : AppCompatActivity() {
    private lateinit var mKendaraanDetailActivity: KendaraanDetailViewModel
    private lateinit var ratingBar: RatingBar
    private lateinit var image: ImageView
    private var kendaraanId: Int? = null
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kendaraan_detail)

        mKendaraanDetailActivity = ViewModelProvider(this)[KendaraanDetailViewModel::class.java]
        DataBindingUtil.setContentView<ActivityKendaraanDetailBinding>(
            this, R.layout.activity_kendaraan_detail
        ).apply {
            this.lifecycleOwner = this@KendaraanDetailActivity
            this.viewmodel = mKendaraanDetailActivity
        }

        image = findViewById(R.id.gambarMobil)
        ratingBar = findViewById(R.id.rb)

        //ratingBar
        ratingBar.setOnRatingBarChangeListener { _, fl, _ ->
            if(fl == 0.0f){
                mKendaraanDetailActivity.deleteRating()
            }else {
                mKendaraanDetailActivity.addupdateRating(Rating(
                        0,
                        kendaraanId!!,
                        userId!!,
                        fl.toInt()
                    )
                )
            }
        }

        //Observe Msg
        mKendaraanDetailActivity.msgDaftarMobil.observe(this@KendaraanDetailActivity){
            if(it != ""){
                Toast.makeText(this@KendaraanDetailActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        mKendaraanDetailActivity.msgRating.observe(this@KendaraanDetailActivity){
            if(it != ""){
                Toast.makeText(this@KendaraanDetailActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        setup()
    }
    fun moveAddPesanan(view: View){
        if(view == findViewById(R.id.BookThisCarBtn)){
            val moveAddPesanan = Intent(this@KendaraanDetailActivity, PemesananActivity::class.java)
            moveAddPesanan.putExtra(sharedPreferencesKey.otherIdKey, kendaraanId)
            startActivity(moveAddPesanan)
        }
    }
    private fun setup(){
        kendaraanId = intent.extras?.getInt(sharedPreferencesKey.otherIdKey)
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        userId = spLogin.getInt(sharedPreferencesKey.idKey, -1)
        getDaftarMobil()
    }
    private fun getDaftarMobil(){
        mKendaraanDetailActivity.setProgressBar(View.VISIBLE)
        mKendaraanDetailActivity.getDaftarMobilAt(kendaraanId!!)
        mKendaraanDetailActivity.codeDaftarMobil.observe(this@KendaraanDetailActivity){
            if(it == 200){
                Glide.with(image)
                    .load(RClient.imageBaseUrl() + mKendaraanDetailActivity.daftarMobil.value!!.image)
                    .into(image)
            }
            if(it!= null){
                mKendaraanDetailActivity.codeDaftarMobil.removeObservers(this@KendaraanDetailActivity)
                getRating()
            }
        }
    }
    private fun getRating(){
        mKendaraanDetailActivity.setProgressBar(View.VISIBLE)
        mKendaraanDetailActivity.getRating(userId!!, kendaraanId!!)
        mKendaraanDetailActivity.codeRating.observe(this@KendaraanDetailActivity){
            if(it!= null){
                mKendaraanDetailActivity.setProgressBar(View.INVISIBLE)
                mKendaraanDetailActivity.codeRating.removeObservers(this@KendaraanDetailActivity)
            }
        }
    }
}