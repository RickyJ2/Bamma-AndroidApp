package com.example.sewakendaraan.activity

import android.annotation.SuppressLint
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
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.ActivityRegisterBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.loginPrefKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.passwordKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.usernameKey
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.viewModel.RegisterViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var mRegisterViewModel: RegisterViewModel
    private val CHANNEL_ID = "channel_notification_register"
    private val notification = 101
    private lateinit var inputLayoutDateOfBirth: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Register"

        //databinding
        mRegisterViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        DataBindingUtil.setContentView<ActivityRegisterBinding>(
            this, R.layout.activity_register
        ).apply{
            this.lifecycleOwner = this@RegisterActivity
            this.viewmodel = mRegisterViewModel
        }

        createNotificationChannel()

        //datepicker
        inputLayoutDateOfBirth = findViewById(R.id.inputLayoutDateOfBirth)
        inputLayoutDateOfBirth.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                datePicker(inputLayoutDateOfBirth)
            }
        }
        //Observe Msg
        mRegisterViewModel.msg.observe(this@RegisterActivity){
            if(it != ""){
                Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    //onClick loginNavBtn
    fun moveLogin(view : View){
        if(view == findViewById(R.id.loginNavBtn)){
            val moveLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(moveLogin)
        }
    }
    //onClick RegisterBtn
    fun register(view: View){
        if(view == findViewById(R.id.registerBtn)){
            mRegisterViewModel.setProgressBar(View.VISIBLE)
            mRegisterViewModel.register()
            mRegisterViewModel.code.observe(this@RegisterActivity) {
                if (it == 200 || it == 405) {
                    val moveLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
                    sendNotification(mRegisterViewModel.registerForm.value?.username.toString(), mRegisterViewModel.msg.value.toString())
                    savePreferences(mRegisterViewModel.registerForm.value?.username.toString())
                    startActivity(moveLogin)
                }
                if(it != null){
                    mRegisterViewModel.setProgressBar(View.INVISIBLE)
                }
            }
        }
    }
    //savePreference for Login Page
    private fun savePreferences(username: String){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spLogin.edit()
        editor.putString(usernameKey,username)
        editor.putString(passwordKey, "")
        editor.apply()
    }
    //datePicker
    fun datePicker(view: View){
        if(view == findViewById(R.id.inputLayoutDateOfBirth)){
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
                mRegisterViewModel.setDatePicker(sdf.format(datePicker.selection))
            }
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
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification(vUsername: String, msg: String){
        val intent = Intent()
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", vUsername)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val picture = BitmapFactory.decodeResource(resources, R.drawable.welcome)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle("Welcome, $vUsername!")
            .setContentText(msg)
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

