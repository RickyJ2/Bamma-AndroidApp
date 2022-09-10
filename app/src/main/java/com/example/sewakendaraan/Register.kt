package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class Register : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputHandphone: TextInputLayout
    private lateinit var inputDateofBirth: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle("Register")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputHandphone = findViewById(R.id.inputLayoutHandphone)
        inputDateofBirth = findViewById(R.id.inputLayoutDateOfBirth)
        val btnRegister: Button = findViewById(R.id.registerBtn)
        val btnNavLogin: Button = findViewById(R.id.loginNavBtn)



        btnNavLogin.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            startActivity(moveLogin)
        })
    }
}