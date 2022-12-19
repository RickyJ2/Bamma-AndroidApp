package com.example.sewakendaraan.activity.pemesanan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.R
import com.example.sewakendaraan.activity.home.HomeActivity
import com.example.sewakendaraan.databinding.ActivityPemesananBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.viewModel.PemesananViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class PemesananActivity : AppCompatActivity() {
    lateinit var mPemesananViewModel: PemesananViewModel
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
        replaceFragment(PemesananFragment())
    }
    fun pesan(){
        if(kendaraanId == null){
            Toast.makeText(this@PemesananActivity, "Pilih Kendaraan yang ingin dibooking...", Toast.LENGTH_SHORT).show()
        }else{
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
    fun cancel(){
        val moveHome = Intent(this@PemesananActivity, HomeActivity::class.java)
        startActivity(moveHome)
    }
    fun cancelSelected(){
        kendaraanId = null
        mPemesananViewModel.setDaftarMobiltoNull()
        mPemesananViewModel.setCardSelected(View.GONE)
    }
    //datePicker
    fun datePicker(view: View){
        if(view == findViewById(R.id.tglPeminjaman)) {
            val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Masukkan Tanggal Peminjaman")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            if(!datePicker.isVisible)
                datePicker.show(supportFragmentManager, datePicker.tag)

            datePicker.addOnPositiveButtonClickListener {
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mPemesananViewModel.setDatePickerPeminjaman(sdf.format(datePicker.selection))
            }
        }else{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Masukkan Tanggal Pengembalian")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            if(!datePicker.isVisible)
                datePicker.show(supportFragmentManager, datePicker.tag)

            datePicker.addOnPositiveButtonClickListener {
                val myFormat = "MM/dd/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                mPemesananViewModel.setDatePickerPengembalian(sdf.format(datePicker.selection))
            }
        }

    }
    private fun setup(){
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        userId = spLogin.getInt(sharedPreferencesKey.idKey, -1)
        if(intent.hasExtra(sharedPreferencesKey.otherIdKey)){
            kendaraanId = intent.extras?.getInt(sharedPreferencesKey.otherIdKey)
            getDaftarMobilAt()
            mPemesananViewModel.setCardSelected(View.VISIBLE)
        }else{
            mPemesananViewModel.setCardSelected(View.GONE)
        }
        getDaftarMobil()
    }
    private fun getDaftarMobilAt(){
        mPemesananViewModel.setProgressBar(View.VISIBLE)
        mPemesananViewModel.getDaftarMobilAt(kendaraanId!!)
        mPemesananViewModel.codeDaftarMobil.observe(this@PemesananActivity){
            if(it!= null){
                mPemesananViewModel.setProgressBar(View.INVISIBLE)
                mPemesananViewModel.codeDaftarMobil.removeObservers(this@PemesananActivity)
            }
        }
    }
    private fun getDaftarMobil(){
        mPemesananViewModel.setProgressBar(View.VISIBLE)
        mPemesananViewModel.getDaftarMobilList()
        mPemesananViewModel.daftarMobilList.observe(this@PemesananActivity){
            if(it != null){
                mPemesananViewModel.setProgressBar(View.INVISIBLE)
                mPemesananViewModel.daftarMobilList.removeObservers(this@PemesananActivity)
            }
        }
    }
    fun movePesan(id: Int){
        kendaraanId = id
        mPemesananViewModel.setCardSelected(View.VISIBLE)
        getDaftarMobilAt()
        replaceFragment(PemesananFragment())
    }
    private fun  replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}