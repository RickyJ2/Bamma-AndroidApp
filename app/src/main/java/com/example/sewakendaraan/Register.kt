package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sewakendaraan.databinding.ActivityRegisterBinding
import com.example.sewakendaraan.room.User
import com.example.sewakendaraan.room.UserDB
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    val db by lazy {UserDB(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle("Register")
        supportActionBar?.hide()

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

       binding.inputLayoutDateOfBirth.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.tag)
        }

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.inputLayoutDateOfBirth.getEditText()?.setText(sdf.format(datePicker.selection))
        }

        binding.loginNavBtn.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            startActivity(moveLogin)
        })


        binding.registerBtn.setOnClickListener(View.OnClickListener {
            var checkRegister = true
            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val email: String = binding.inputLayoutEmail.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()
            val handphone: String = binding.inputLayoutHandphone.getEditText()?.getText().toString()
            val dateOfBirth: String = binding.inputLayoutDateOfBirth.getEditText()?.getText().toString()

            if(username.isEmpty()){
                binding.inputLayoutUsername.setError("Username must be filled with text")
                checkRegister = false
            }

            if(password.isEmpty()){
                binding.inputLayoutPassword.setError("Password must be filled with text")
                checkRegister = false
            }else if(password.length < 8){
                binding.inputLayoutPassword.setError("Password must at least 8 characters")
                checkRegister = false
            }

            if(email.isEmpty()){
                binding.inputLayoutEmail.setError("Email must be filled with text")
                checkRegister = false
            }

            if(handphone.isEmpty()){
                binding.inputLayoutHandphone.setError("Handphone must be filled with text")
                checkRegister = false
            }

            if(dateOfBirth.isEmpty()){
                binding.inputLayoutDateOfBirth.setError("Date of Birth must be filled with text")
                checkRegister = false
            }

            if(!binding.checkToggle.isChecked){
                Snackbar.make(binding.register, "You must check the agreeement", Snackbar.LENGTH_LONG).show()
                checkRegister = false
            }

            if(!checkRegister) return@OnClickListener
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(0, email, username, password, dateOfBirth, handphone)
                )
            }
            val mBundle = Bundle()
            mBundle.putString("username", username)
            moveLogin.putExtra("register", mBundle)
            startActivity(moveLogin)
        })
    }
}

