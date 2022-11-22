package com.example.sewakendaraan

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.firstTimePrefKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.installKey

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private var spFirstTime: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        spFirstTime = getSharedPreferences(firstTimePrefKey, Context.MODE_PRIVATE)
        val firstTime: String = spFirstTime!!.getString(installKey,"").toString()

        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        val editorLogin: SharedPreferences.Editor = spLogin!!.edit()
        editorLogin.putInt(sharedPreferencesKey.idKey, -1)
        editorLogin.apply()

        if(firstTime == "NO"){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val editorFT: SharedPreferences.Editor = spFirstTime!!.edit()
            editorFT.putString(installKey,"NO")
            editorFT.apply()
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}