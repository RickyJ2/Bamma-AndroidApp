package com.example.sewakendaraan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.idKey
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.viewModel.UserViewModel
import kotlinx.coroutines.runBlocking

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        loginSetup()
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
    private fun loginSetup(){
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        val id = spLogin!!.getInt(idKey, -1)
        if(id == -1){
            val moveLogin = Intent(this@Home, LoginActivity::class.java)
            Toast.makeText(this@Home, "Login Failed", Toast.LENGTH_SHORT).show()
            startActivity(moveLogin)
        }else{
            runBlocking {
                mUserViewModel.setUserData(id)
            }
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}