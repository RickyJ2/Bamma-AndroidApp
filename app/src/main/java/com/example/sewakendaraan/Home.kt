package com.example.sewakendaraan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.room.Constant

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        replaceFragment(HomeFragment())

        binding.bottomNavigationView.background = null
        binding.addFB.setOnClickListener{
            val fragment: Fragment = EditKendaraanFragment()
            val args = Bundle()
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
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}