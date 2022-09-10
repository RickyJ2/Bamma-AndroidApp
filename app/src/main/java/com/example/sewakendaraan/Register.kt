package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputHandphone: TextInputLayout
    private lateinit var inputDateofBirth: TextInputLayout
    private lateinit var layoutRegister: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle("Register")
        supportActionBar?.hide()

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputHandphone = findViewById(R.id.inputLayoutHandphone)
        inputDateofBirth = findViewById(R.id.inputLayoutDateOfBirth)
        layoutRegister = findViewById(R.id.register)
        val toggleBtn: ToggleButton = findViewById(R.id.checkToggle)
        val btnRegister: Button = findViewById(R.id.registerBtn)
        val btnNavLogin: Button = findViewById(R.id.loginNavBtn)

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

       inputDateofBirth.setEndIconOnClickListener {
            datePicker.show(supportFragmentManager, datePicker.tag)
        }

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            inputDateofBirth.getEditText()?.setText(sdf.format(datePicker.selection))
        }

        btnNavLogin.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            startActivity(moveLogin)
        })


        btnRegister.setOnClickListener(View.OnClickListener {
            var checkRegister = true
            val username: String = inputUsername.getEditText()?.getText().toString()
            val email: String = inputEmail.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()
            val handphone: String = inputHandphone.getEditText()?.getText().toString()
            val dateOfBirth: String = inputDateofBirth.getEditText()?.getText().toString()

            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkRegister = false
            }

            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkRegister = false
            }else if(password.length < 8){
                inputPassword.setError("Password must at least 8 characters")
                checkRegister = false
            }

            if(email.isEmpty()){
                inputEmail.setError("Email must be filled with text")
                checkRegister = false
            }

            if(handphone.isEmpty()){
                inputHandphone.setError("Handphone must be filled with text")
                checkRegister = false
            }

            if(dateOfBirth.isEmpty()){
                inputDateofBirth.setError("Date of Birth must be filled with text")
                checkRegister = false
            }

            if(!toggleBtn.isChecked){
                Snackbar.make(layoutRegister, "You must check the agreeement", Snackbar.LENGTH_LONG).show()
                checkRegister = false
            }

            if(!checkRegister) return@OnClickListener
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            val mBundle = Bundle()
            mBundle.putString("username", username)
            mBundle.putString("password", password)
            moveLogin.putExtra("register", mBundle)
            startActivity(moveLogin)
        })
    }
}

