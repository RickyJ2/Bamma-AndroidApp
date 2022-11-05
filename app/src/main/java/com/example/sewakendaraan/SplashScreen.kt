package com.example.sewakendaraan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private val firstTimeInstall = "firstTimeKey"
    private val myPreferences = "PREFERENCES"
    var sharedPreferences: SharedPreferences? = null
    val db by lazy { UserDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        var firstTime: String = sharedPreferences!!.getString(firstTimeInstall,"").toString()

        if(firstTime == "NO"){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(0, "admin", "admin", "admin", "01/01/22", "08123456789")
                )
            }
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString(firstTimeInstall,"NO")
            editor.apply()
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000)
        }
    }
}