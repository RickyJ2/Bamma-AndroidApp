package com.example.sewakendaraan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity(){

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTitle("Sewa Kendaraan")


        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLoqin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                inputUsername.setError("Required")
                checkLoqin = false
            }

            if (password.isEmpty()) {
                inputPassword.setError("Required")
                checkLoqin = false
            }

            if (username ==
                "admin" && password ==
                "admin"
            )checkLoqin = true
            if (!checkLoqin) return@OnClickListener
        })
    }
}