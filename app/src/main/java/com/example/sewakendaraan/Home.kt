package com.example.sewakendaraan

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.sewakendaraan.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var welcomeTextView: TextView
    lateinit var mBundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        welcomeTextView = findViewById(R.id.tvWelcome)

        if(intent.hasExtra("login")){
            getBundle()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.settings -> {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@Home)
                    builder.setMessage("Are you sure want to logout?")
                        .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                            override fun onClick(dialogInterface: DialogInterface, i: Int){
                                finishAndRemoveTask()
                            }
                        }).show()
                }

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

    fun getBundle(){
        mBundle = intent.getBundleExtra("login")!!
        if(!mBundle.isEmpty){
            val mFragmentManager = supportFragmentManager
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            val mFragment = HomeFragment()

            mFragment.arguments = mBundle
            mFragmentTransaction.add(R.id.frameLayout, mFragment).commit()
        }
    }
}