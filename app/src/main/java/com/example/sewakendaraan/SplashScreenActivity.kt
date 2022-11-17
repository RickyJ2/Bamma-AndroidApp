package com.example.sewakendaraan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val firstTimeInstall = "firstTimeKey"
    private val myPreferences = "PREFERENCES"
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val firstTime: String = sharedPreferences!!.getString(firstTimeInstall,"").toString()

        if(firstTime == "NO"){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString(firstTimeInstall,"NO")
            editor.apply()
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}