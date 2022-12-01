package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.databinding.ActivityRegisterBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.loginPrefKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.passwordKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.usernameKey
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.viewModel.UserViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    private lateinit var mUserViewModel: UserViewModel
    private val CHANNEL_ID = "channel_notification_register"
    private val notification = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Register"

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        createNotificationChannel()
        //datepicker
        binding.inputLayoutDateOfBirth.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                datePicker()
            }
        }
        binding.inputLayoutDateOfBirth.editText?.setOnClickListener{
            datePicker()
        }
        //Move to Login Activity
        binding.loginNavBtn.setOnClickListener{
            val moveLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(moveLogin)
        }
        //validationWhileInputing
        validation()
        //Register
        binding.registerBtn.setOnClickListener(View.OnClickListener {
            if(!binding.checkToggle.isChecked){
                Snackbar.make(binding.register, "You must check the agreement", Snackbar.LENGTH_LONG).show()
            }else if(!inputCheck()){
                return@OnClickListener
            }else{
                registerUser()
                val moveLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                sendNotification(binding.inputLayoutUsername.editText?.text.toString())
                savePreferences(binding.inputLayoutUsername.editText?.text.toString())
                startActivity(moveLogin)
            }
        })
    }
    private fun validation(){
        binding.inputLayoutUsername.editText?.doAfterTextChanged {
            if(binding.inputLayoutUsername.editText?.text.toString().isEmpty()){
                binding.inputLayoutUsername.error = "Username must be filled with text"
            }else{
                binding.inputLayoutUsername.error = null
            }
        }
        binding.inputLayoutEmail.editText?.doAfterTextChanged {
            if(binding.inputLayoutEmail.editText?.text.toString().isEmpty()){
                binding.inputLayoutEmail.error = "Email must be filled with text"
            }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputLayoutEmail.editText?.text.toString()).matches()){
                binding.inputLayoutEmail.error = "Email Invalid"
            }else{
                binding.inputLayoutEmail.error = null
            }
        }
        binding.inputLayoutPassword.editText?.doAfterTextChanged {
            if(binding.inputLayoutPassword.editText?.text.toString().isEmpty()){
                binding.inputLayoutPassword.error = "Password must be filled with text"
            }else if(binding.inputLayoutPassword.editText?.text.toString().length < 8){
                binding.inputLayoutPassword.error = "Password must at least 8 characters"
            }else{
                binding.inputLayoutPassword.error = null
            }
        }
        binding.inputLayoutHandphone.editText?.doAfterTextChanged{
            if(binding.inputLayoutHandphone.editText?.text.toString().isEmpty()){
                binding.inputLayoutHandphone.error = "Handphone must be filled"
            }else{
                binding.inputLayoutHandphone.error = null
            }
        }
        binding.inputLayoutDateOfBirth.editText?.doAfterTextChanged{
            if(binding.inputLayoutDateOfBirth.editText?.text.toString().isEmpty()){
                binding.inputLayoutDateOfBirth.error = "Date of Birth must be filled"
            }else{
                binding.inputLayoutDateOfBirth.error = null
            }
        }
    }
    private fun inputCheck(): Boolean{
        if(binding.inputLayoutUsername.editText?.text.toString().isEmpty()){
            binding.inputLayoutUsername.error = "Username must be filled with text"
        }else{
            binding.inputLayoutUsername.error = null
        }
        if(binding.inputLayoutEmail.editText?.text.toString().isEmpty()){
            binding.inputLayoutEmail.error = "Email must be filled with text"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.inputLayoutEmail.editText?.text.toString()).matches()){
            binding.inputLayoutEmail.error = "Email Invalid"
        }else{
            binding.inputLayoutEmail.error = null
        }
        if(binding.inputLayoutPassword.editText?.text.toString().isEmpty()){
            binding.inputLayoutPassword.error = "Password must be filled with text"
        }else if(binding.inputLayoutPassword.editText?.text.toString().length < 8){
            binding.inputLayoutPassword.error = "Password must at least 8 characters"
        }else{
            binding.inputLayoutPassword.error = null
        }
        if(binding.inputLayoutHandphone.editText?.text.toString().isEmpty()){
            binding.inputLayoutHandphone.error = "Handphone must be filled"
        }else{
            binding.inputLayoutHandphone.error = null
        }
        if(binding.inputLayoutDateOfBirth.editText?.text.toString().isEmpty()){
            binding.inputLayoutDateOfBirth.error = "Date of Birth must be filled"
        }else{
            binding.inputLayoutDateOfBirth.error = null
        }

        return (
            binding.inputLayoutUsername.error == null &&
            binding.inputLayoutPassword.error == null &&
            binding.inputLayoutEmail.error == null &&
            binding.inputLayoutHandphone.error == null &&
            binding.inputLayoutDateOfBirth.error == null
            )
    }
    private fun registerUser(){
        val username: String = binding.inputLayoutUsername.editText?.text.toString()
        val email: String = binding.inputLayoutEmail.editText?.text.toString()
        val password: String = binding.inputLayoutPassword.editText?.text.toString()
        val handphone: String = binding.inputLayoutHandphone.editText?.text.toString()
        val dateOfBirth: String = binding.inputLayoutDateOfBirth.editText?.text.toString()

        val user = User(
            0,
            username,
            email,
            password,
            dateOfBirth,
            handphone
        )
        mUserViewModel.addUser(user)
    }
    private fun savePreferences(username: String){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spLogin!!.edit()
        editor.putString(usernameKey,username)
        editor.putString(passwordKey, "")
        editor.apply()
    }
    private fun datePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        if(!datePicker.isVisible)
            datePicker.show(supportFragmentManager, datePicker.tag)

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            binding.inputLayoutDateOfBirth.editText?.setText(sdf.format(datePicker.selection))
        }
    }
    private fun createNotificationChannel() {
        val name = "Notification Title"
        val descriptionText = "Notification Description"

        val channel = NotificationChannel(CHANNEL_ID,name,
            NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    private fun sendNotification(vUsername: String){
        val intent = Intent()
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", vUsername)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val picture = BitmapFactory.decodeResource(resources, R.drawable.welcome)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle(vUsername)
            .setContentText("Berhasil Register")
            .setLargeIcon(picture)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(picture)
                    .bigLargeIcon(null))
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(false)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notification, builder.build())
        }
    }
}

