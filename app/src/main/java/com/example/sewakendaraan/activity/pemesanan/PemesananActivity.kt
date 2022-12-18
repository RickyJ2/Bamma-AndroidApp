package com.example.sewakendaraan.activity.pemesanan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.sewakendaraan.R
import com.example.sewakendaraan.activity.home.HomeActivity
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.databinding.ActivityPemesananBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.viewModel.PemesananViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class PemesananActivity : AppCompatActivity() {
    private lateinit var mPemesananViewModel: PemesananViewModel
    private lateinit var inputLayoutTanggal: TextInputLayout
    private lateinit var inputLayoutDurasi: TextInputLayout
    private lateinit var image: ImageView
    private var kendaraanId: Int? = null
    private var userId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan)

        mPemesananViewModel = ViewModelProvider(this)[PemesananViewModel::class.java]
        DataBindingUtil.setContentView<ActivityPemesananBinding>(
            this, R.layout.activity_pemesanan
        ).apply {
            this.lifecycleOwner = this@PemesananActivity
            this.viewmodel = mPemesananViewModel
        }
        image = findViewById(R.id.gambarMobil)

        //datePicker dan TimePicker
        inputLayoutTanggal = findViewById(R.id.inputLayoutTanggal)
        inputLayoutTanggal.editText?.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                datePicker(inputLayoutTanggal)
            }
        }
        inputLayoutDurasi = findViewById(R.id.inputLayoutDurasi)
        inputLayoutDurasi.editText?.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                timePicker(inputLayoutDurasi)
            }
        }
        //observe msg
        mPemesananViewModel.msgPemesanan.observe(this@PemesananActivity){
            if(it != ""){
                Toast.makeText(this@PemesananActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        mPemesananViewModel.msgDaftarMobil.observe(this@PemesananActivity){
            if(it != ""){
                Toast.makeText(this@PemesananActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        setup()
    }
    fun pesan(view: View){
        if(kendaraanId == null){
            Toast.makeText(this@PemesananActivity, "Pilih Kendaraan yang ingin dibooking...", Toast.LENGTH_SHORT).show()
        }else if(view == findViewById(R.id.pesanBtn)){
            mPemesananViewModel.setProgressBar(View.VISIBLE)
            mPemesananViewModel.addPemesanan(kendaraanId!!, userId!!)
            mPemesananViewModel.codePemesanan.observe(this@PemesananActivity){
                if(it == 200){
                    val moveHome = Intent(this@PemesananActivity, HomeActivity::class.java)
                    startActivity(moveHome)
                }
                if (it != null){
                    mPemesananViewModel.setProgressBar(View.INVISIBLE)
                }
            }
        }
    }
    fun cancel(view: View){
        if(view == findViewById(R.id.cancelBtn)){
            val moveHome = Intent(this@PemesananActivity, HomeActivity::class.java)
            startActivity(moveHome)
        }
    }
    fun cancelSelected(view: View){
        if(view == findViewById(R.id.cancelSelect)){
            kendaraanId = null
            mPemesananViewModel.setCardSelected(View.GONE)
        }
    }
    fun selectCar(view: View){
        if(view == findViewById(R.id.selectBtn)){
            val moveSelectCar = Intent(this@PemesananActivity, SelectCarActivity::class.java)
            startActivity(moveSelectCar)
        }
    }
    //datePicker
    fun datePicker(view: View){
        if(view == findViewById(R.id.inputLayoutTanggal)){
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select your birth of date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            if(!datePicker.isVisible)
                datePicker.show(supportFragmentManager, datePicker.tag)

            datePicker.addOnPositiveButtonClickListener {
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mPemesananViewModel.setDatePicker(sdf.format(datePicker.selection))
            }
        }
    }
    //timePicker
    fun timePicker(view: View){
        if(view == findViewById(R.id.inputLayoutDurasi)){

        }
    }
    private fun setup(){
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        userId = spLogin.getInt(sharedPreferencesKey.idKey, -1)
        if(intent.hasExtra(sharedPreferencesKey.otherIdKey)){
            kendaraanId = intent.extras?.getInt(sharedPreferencesKey.otherIdKey)
            getDaftarMobil()
            mPemesananViewModel.setCardSelected(View.VISIBLE)
        }else{
            mPemesananViewModel.setCardSelected(View.GONE)
        }
    }
    private fun getDaftarMobil(){
        mPemesananViewModel.setProgressBar(View.VISIBLE)
        mPemesananViewModel.getDaftarMobilAt(kendaraanId!!)
        mPemesananViewModel.codeDaftarMobil.observe(this@PemesananActivity){
            if(it == 200){
                Glide.with(image)
                    .load(RClient.imageBaseUrl() + mPemesananViewModel.daftarMobil.value!!.image)
                    .into(image)
            }
            if(it!= null){
                mPemesananViewModel.setProgressBar(View.INVISIBLE)
                mPemesananViewModel.codeDaftarMobil.removeObservers(this@PemesananActivity)
            }
        }
    }
}