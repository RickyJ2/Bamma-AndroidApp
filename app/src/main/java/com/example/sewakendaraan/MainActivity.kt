package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sewakendaraan.databinding.ActivityMainBinding
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.room.UserDB
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding

    private lateinit var layoutMain: ConstraintLayout
    lateinit var mBundle: Bundle
    val db by lazy { UserDB(this) }

    private val myPreference = "myPref"
    private val usernameKey = "nameKey"
    private val passwordKey = "passKey"
    var sharedPreferences: SharedPreferences? = null

    lateinit var vUsername: String

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notification = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setTitle("Sewa Kendaraan")
        createNotificationChannel()
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        if(sharedPreferences!!.contains(usernameKey)){
            binding.inputLayoutUsername?.editText?.setText(sharedPreferences!!.getString(usernameKey,""))
        }
        if(sharedPreferences!!.contains(passwordKey)){
            binding.inputLayoutPassword?.editText?.setText(sharedPreferences!!.getString(passwordKey,""))
        }
        binding.registerNavBtn.setOnClickListener(View.OnClickListener {
            val moveRegister = Intent(this@MainActivity, Register::class.java)
            startActivity(moveRegister)
        })

        if(intent.hasExtra("register")){
            getBundle()
            sendNotification1()
        }else{
            vUsername = "admin"
        }

        binding.btnLogin.setOnClickListener(View.OnClickListener {
            val username: String = binding.inputLayoutUsername.getEditText()?.getText().toString()
            val password: String = binding.inputLayoutPassword.getEditText()?.getText().toString()

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Required")
            }

            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Required")
            }

            if(!username.isEmpty() && !password.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val users = db.userDao().getUsernamePassword(username, password)
                    if (users != null){
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(usernameKey,username)
                        editor.putString(passwordKey, password)
                        editor.apply()
                        val moveHome = Intent(this@MainActivity, Home::class.java)
                        val mBundleL = Bundle()
                        mBundleL.putInt("user_id", users.id)
                        mBundleL.putString("username", users.username)
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
            binding.inputLayoutUsername.getEditText()?.setText(vUsername)
            binding.inputLayoutPassword.getEditText()?.setText("")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1,name,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(){
        val intent : Intent = Intent()
        /*val intent : Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", vUsername)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val picture = BitmapFactory.decodeResource(resources, R.drawable.welcome)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
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