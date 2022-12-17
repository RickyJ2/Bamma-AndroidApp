package com.example.sewakendaraan.activity.kritikSaran

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.ActivityKritikSaranBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.viewModel.KritikSaranViewModel

class KritikSaranActivity : AppCompatActivity() {
    lateinit var mKritikSaranViewModel: KritikSaranViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kritik_saran)

        mKritikSaranViewModel = ViewModelProvider(this)[KritikSaranViewModel::class.java]
        DataBindingUtil.setContentView<ActivityKritikSaranBinding>(
            this, R.layout.activity_kritik_saran
        ).apply{
            this.lifecycleOwner = this@KritikSaranActivity
            this.viewmodel = mKritikSaranViewModel
        }
        getKritikSaran()

        //Observe Msg
        mKritikSaranViewModel.msg.observe(this@KritikSaranActivity){
            if(it != ""){
                Toast.makeText(this@KritikSaranActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getKritikSaran(){
        mKritikSaranViewModel.setProgressBar(View.VISIBLE)
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        val id = spLogin.getInt(sharedPreferencesKey.idKey, -1)
        mKritikSaranViewModel.getKritikSaran(id)
        mKritikSaranViewModel.setIdUser(id)
        mKritikSaranViewModel.code.observe(this@KritikSaranActivity){
            if(it != null){
                mKritikSaranViewModel.setProgressBar(View.INVISIBLE)
                mKritikSaranViewModel.code.removeObservers(this@KritikSaranActivity)
                replaceFragment(KritikSaranFragment())
            }
        }
    }
    fun addKritikSaran(){
        mKritikSaranViewModel.setProgressBar(View.VISIBLE)
        mKritikSaranViewModel.addKritikSaran()
        mKritikSaranViewModel.code.observe(this@KritikSaranActivity){
            if(it == 200){
                replaceFragment(KritikSaranFragment())
            }
            if(it != null){
                mKritikSaranViewModel.setProgressBar(View.INVISIBLE)
                mKritikSaranViewModel.code.removeObservers(this@KritikSaranActivity)
            }
        }
    }
    fun updateKritikSaran(){
        mKritikSaranViewModel.setProgressBar(View.VISIBLE)
        mKritikSaranViewModel.updateKritikSaran()
        mKritikSaranViewModel.code.observe(this@KritikSaranActivity){
            if(it == 200){
                replaceFragment(KritikSaranFragment())
            }
            if(it != null){
                mKritikSaranViewModel.setProgressBar(View.INVISIBLE)
                mKritikSaranViewModel.code.removeObservers(this@KritikSaranActivity)
            }
        }
    }
    fun deleteKritikSaran(id: Int){
        mKritikSaranViewModel.setProgressBar(View.VISIBLE)
        mKritikSaranViewModel.deleteKritikSaran(id)
        mKritikSaranViewModel.code.observe(this@KritikSaranActivity){
            if(it != null){
                mKritikSaranViewModel.setProgressBar(View.INVISIBLE)
                mKritikSaranViewModel.code.removeObservers(this@KritikSaranActivity)
            }
        }
    }

    fun  replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}