package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.doAfterTextChanged
import com.example.sewakendaraan.databinding.ActivityRegisterBinding
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDB
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class Register : AppCompatActivity() {
    private lateinit var  binding: ActivityRegisterBinding
    val db by lazy { UserDB(this) }

    private val CHANNEL_ID = "channel_notification_register"
    private val notification = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Register"
        supportActionBar?.hide()

        createNotificationChannel()

        //Panggil DatePicker
        binding.inputLayoutDateOfBirth.editText?.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                datePicker()
            }
        }
        binding.inputLayoutDateOfBirth.editText?.setOnClickListener{
            datePicker()
        }

        //Move to Login Activity
        binding.loginNavBtn.setOnClickListener(View.OnClickListener {
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            startActivity(moveLogin)
        })

        //Validation
        binding.inputLayoutUsername.editText?.doAfterTextChanged {
            if(binding.inputLayoutUsername.editText?.text.toString().isEmpty()){
                binding.inputLayoutUsername.setError("Username must be filled with text")
            }else{
                binding.inputLayoutUsername.setError(null)
            }
        }
        binding.inputLayoutEmail.editText?.doAfterTextChanged {
            if(binding.inputLayoutEmail.editText?.text.toString().isEmpty()){
                binding.inputLayoutEmail.setError("Email must be filled with text")
            }else{
                binding.inputLayoutEmail.setError(null)
            }
        }
        binding.inputLayoutPassword.editText?.doAfterTextChanged {
            if(binding.inputLayoutPassword.editText?.text.toString().isEmpty()){
                binding.inputLayoutPassword.setError("Password must be filled with text")
            }else if(binding.inputLayoutPassword.editText?.text.toString().length < 8){
                binding.inputLayoutPassword.setError("Password must at least 8 characters")
            }else{
                binding.inputLayoutPassword.setError(null)
            }
        }
        binding.inputLayoutHandphone.editText?.doAfterTextChanged{
            if(binding.inputLayoutHandphone.editText?.text.toString().isEmpty()){
                binding.inputLayoutHandphone.setError("Handphone must be filled")
            }else{
                binding.inputLayoutHandphone.setError(null)
            }
        }
        binding.inputLayoutDateOfBirth.editText?.doAfterTextChanged{
            if(binding.inputLayoutDateOfBirth.editText?.text.toString().isEmpty()){
                binding.inputLayoutDateOfBirth.setError("Date of Birth must be filled")
            }else{
                binding.inputLayoutDateOfBirth.setError(null)
            }
        }

        //Register
        binding.registerBtn.setOnClickListener(View.OnClickListener {
            var checkRegister = true
            val username: String = binding.inputLayoutUsername.editText?.text.toString()
            val email: String = binding.inputLayoutEmail.editText?.text.toString()
            val password: String = binding.inputLayoutPassword.editText?.text.toString()
            val handphone: String = binding.inputLayoutHandphone.editText?.text.toString()
            val dateOfBirth: String = binding.inputLayoutDateOfBirth.editText?.text.toString()

            if(username.isEmpty()){
                binding.inputLayoutUsername.setError("Username must be filled with text")
                checkRegister = false
            }
            if(email.isEmpty()){
                binding.inputLayoutEmail.setError("Email must be filled with text")
                checkRegister = false
            }
            if(password.isEmpty()){
                binding.inputLayoutPassword.setError("Password must be filled with text")
                checkRegister = false
            }else if(password.length < 8){
                binding.inputLayoutPassword.setError("Password must at least 8 characters")
                checkRegister = false
            }

            if(handphone.isEmpty()){
                binding.inputLayoutHandphone.setError("Handphone must be filled")
                checkRegister = false
            }
            if(dateOfBirth.isEmpty()){
                binding.inputLayoutDateOfBirth.setError("Date of Birth must be filled")
                checkRegister = false
            }

            if(!binding.checkToggle.isChecked){
                Snackbar.make(binding.register, "You must check the agreeement", Snackbar.LENGTH_LONG).show()
                checkRegister = false
            }

            if(!checkRegister)return@OnClickListener
            val moveLogin = Intent(this@Register, MainActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().addUser(
                    User(0, email, username, password, dateOfBirth, handphone)
                )
                sendNotification(username)
            }
            val mBundle = Bundle()
            mBundle.putString("username", username)
            moveLogin.putExtra("register", mBundle)
            startActivity(moveLogin)
        })
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
    }

    private fun sendNotification(vUsername: String){
        val intent : Intent = Intent()
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

