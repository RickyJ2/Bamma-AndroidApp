package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnNavRegister:Button = findViewById(R.id.registerNavBtn)
        btnNavRegister.setOnClickListener(View.OnClickListener {
            val moveRegister = Intent(this@MainActivity, Register::class.java)
            startActivity(moveRegister)
        })
    }
}