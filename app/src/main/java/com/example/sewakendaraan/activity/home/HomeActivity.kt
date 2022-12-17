package com.example.sewakendaraan.activity.home

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
//import com.example.sewakendaraan.EditKendaraanFragment
import com.example.sewakendaraan.HomeFragment
import com.example.sewakendaraan.R
import com.example.sewakendaraan.activity.LoginActivity
import com.example.sewakendaraan.activity.profile.ProfileActivity
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.idKey
import com.example.sewakendaraan.viewModel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var mHomeViewModel: HomeViewModel
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        DataBindingUtil.setContentView<ActivityHomeBinding>(
            this, R.layout.activity_home
        ).apply {
            this.lifecycleOwner = this@HomeActivity
            this.viewmodel = mHomeViewModel
        }

        loginSetup()
        daftarMobilSetup()
        replaceFragment(HomeFragment())

        //Observe Msg
        mHomeViewModel.msgUser.observe(this@HomeActivity){
            if(it != ""){
                Toast.makeText(this@HomeActivity, it,Toast.LENGTH_SHORT).show()
            }
        }
        mHomeViewModel.msgDaftarMobil.observe(this@HomeActivity){
            if(it != ""){
                Toast.makeText(this@HomeActivity, it,Toast.LENGTH_SHORT).show()
            }
        }
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        //binding.bottomNavigationView.background = null

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                else -> {
                }
            }
            true
        }
    }
    fun moveAddPesanan(view: View){
//        if(view == findViewById(R.id.addFB)){
//            val fragment: Fragment = EditKendaraanFragment()
//            val args = Bundle()
//            args.putInt("arg_type", Constant.TYPE_CREATE)
//            fragment.arguments = args
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.frameLayout,fragment)
//            fragmentTransaction.commit()
//        }
    }
    fun moveProfile(){
        val moveProfile = Intent(this@HomeActivity, ProfileActivity::class.java)
        startActivity(moveProfile)
    }
    private fun loginSetup(){
        mHomeViewModel.setProgressBar(View.VISIBLE)
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        val id = spLogin.getInt(idKey, -1)
        if(id == -1){
            mHomeViewModel.setProgressBar(View.INVISIBLE)
            val moveLogin = Intent(this@HomeActivity, LoginActivity::class.java)
            Toast.makeText(this@HomeActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            startActivity(moveLogin)
        }else{
            mHomeViewModel.userData(id)
            mHomeViewModel.codeUser.observe(this@HomeActivity){
                if(it != null){
                    mHomeViewModel.setProgressBar(View.INVISIBLE)
                    mHomeViewModel.codeUser.removeObservers(this@HomeActivity)
                }
            }
        }
    }
    fun daftarMobilSetup(){
        mHomeViewModel.setProgressBar(View.VISIBLE)
        mHomeViewModel.getDaftarMobil()
        mHomeViewModel.codeDaftarMobil.observe(this@HomeActivity){
            if(it != null){
                mHomeViewModel.setProgressBar(View.INVISIBLE)
                mHomeViewModel.codeDaftarMobil.removeObservers(this@HomeActivity)
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