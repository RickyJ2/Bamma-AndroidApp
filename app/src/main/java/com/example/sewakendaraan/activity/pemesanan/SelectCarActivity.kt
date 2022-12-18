package com.example.sewakendaraan.activity.pemesanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.ActivitySelectCarBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.rv.RVItemDaftarMobilSmall
import com.example.sewakendaraan.viewModel.SelectCarViewModel

class SelectCarActivity : AppCompatActivity() {
    private lateinit var  mSelectCarViewModel: SelectCarViewModel
    private lateinit var rvKendaraan: RecyclerView
    private lateinit var adapter: RVItemDaftarMobilSmall

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_car)

        mSelectCarViewModel = ViewModelProvider(this)[SelectCarViewModel::class.java]
        DataBindingUtil.setContentView<ActivitySelectCarBinding>(
            this, R.layout.activity_select_car
        ).apply {
            this.lifecycleOwner = this@SelectCarActivity
            this.viewmodel = mSelectCarViewModel
        }
        val layoutManager = LinearLayoutManager(this@SelectCarActivity)
        rvKendaraan = findViewById(R.id.rvKendaraan)
        adapter = RVItemDaftarMobilSmall(arrayListOf()){
            movePesan(it.id)
        }

        rvKendaraan.layoutManager = layoutManager
        rvKendaraan.adapter = adapter

        mSelectCarViewModel.msg.observe(this@SelectCarActivity){
            if(it != ""){
                Toast.makeText(this@SelectCarActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
        getDaftarMobil()
    }
    private fun getDaftarMobil(){
        mSelectCarViewModel.setProgressBar(View.VISIBLE)
        mSelectCarViewModel.getDaftarMobil()
        mSelectCarViewModel.daftarMobilList.observe(this@SelectCarActivity){
            if(it != null){
                adapter.setData(it)
                mSelectCarViewModel.setProgressBar(View.INVISIBLE)
                mSelectCarViewModel.daftarMobilList.removeObservers(this@SelectCarActivity)
            }
        }
    }
    fun moveCancel(view: View){
        if(view == findViewById(R.id.cancel)){
            val moveCancel = Intent(this@SelectCarActivity, PemesananActivity::class.java)
            startActivity(moveCancel)
        }
    }
    private fun movePesan(id: Int){
        val movePesan = Intent(this@SelectCarActivity, PemesananActivity::class.java)
        movePesan.putExtra(sharedPreferencesKey.otherIdKey, id)
        startActivity(movePesan)
    }
}