package com.example.sewakendaraan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sewakendaraan.room.UserDB
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var layoutMain: ConstraintLayout
    lateinit var mBundle: Bundle
    val db by lazy { UserDB(this) }
    private val myPreference = "myPref"
    private val usernameKey = "nameKey"
    private val passwordKey = "passKey"
    var sharedPreferences: SharedPreferences? = null

    lateinit var vUsername: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Sewa Kendaraan")
        supportActionBar?.hide()
        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        layoutMain = findViewById(R.id.main)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnNavRegister:Button = findViewById(R.id.registerNavBtn)

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        if(sharedPreferences!!.contains(usernameKey)){
            inputUsername?.editText?.setText(sharedPreferences!!.getString(usernameKey,""))
        }
        if(sharedPreferences!!.contains(passwordKey)){
            inputPassword?.editText?.setText(sharedPreferences!!.getString(passwordKey,""))
        }
        btnNavRegister.setOnClickListener(View.OnClickListener {
            val moveRegister = Intent(this@MainActivity, Register::class.java)
            startActivity(moveRegister)
        })

        if(intent.hasExtra("register")){
            getBundle()
        }else{
            vUsername = "admin"
            vPassword = "admin"
        }

        btnLogin.setOnClickListener(View.OnClickListener {
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Required")
            }

            if (password.isEmpty()) {
                inputPassword.setError("Required")
            }

            if(!username.isEmpty() && !password.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val users = db.userDao().getUsernamePassword(username, password)
                    //Log.d("MainActivity", "dbResponses: $users")
                    if (users != null){
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(usernameKey,username)
                        editor.putString(passwordKey, password)
                        editor.apply()
                        val moveHome = Intent(this@MainActivity, Home::class.java)
                        val mBundleL = Bundle()
                        mBundleL.putString("username", username)
                        moveHome.putExtra("login", mBundleL)
                        startActivity(moveHome)
                    }else{
                        Snackbar.make(layoutMain, "User not found!", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
            return@OnClickListener
        })
    }

    fun getBundle(){
        mBundle = intent.getBundleExtra("register")!!
        if(!mBundle.isEmpty){
            vUsername = mBundle.getString("username")!!
            vPassword = mBundle.getString("password")!!
            inputUsername.getEditText()?.setText(vUsername)
        }
    }
}