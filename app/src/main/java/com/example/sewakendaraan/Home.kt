package com.example.sewakendaraan

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.room.Constant

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var mBundle: Bundle
    lateinit var vUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        setContentView(binding.root)
        if(intent.hasExtra("login")){
            getBundle()
        }else{
            vUsername = "admin"
        }

        replaceFragment(HomeFragment())
        binding.bottomNavigationView.background = null
        binding.addFB.setOnClickListener{
            val fragment: Fragment = EditKendaraanFragment()
            val args = Bundle()
            args.putInt("arg_id", 0)
            args.putInt("arg_type", Constant.TYPE_CREATE)
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,fragment)
            fragmentTransaction.commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                else -> {

                }
            }
            true
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val args = Bundle()
        args.putString("username", vUsername)
        fragment.arguments = args
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
    fun getBundle(){
        mBundle = intent.getBundleExtra("login")!!
        if(!mBundle.isEmpty){
            vUsername = mBundle.getString("username")!!
        }
    }
}