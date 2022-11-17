package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.doAfterTextChanged
import com.example.sewakendaraan.databinding.ActivityMainBinding
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.room.userRoom.UserDB
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityMainBinding

    private lateinit var layoutMain: ConstraintLayout
    lateinit var mBundle: Bundle
    val db by lazy { UserDB(this) }

    private val myPreference = "myPref"
    private val usernameKey = "nameKey"
    private val passwordKey = "passKey"
    var sharedPreferences: SharedPreferences? = null

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notification1 = 101
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notification2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Login"
        supportActionBar?.hide()

        createNotificationChannel()

        //SharedPreferences akun login terakhir kali
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        if(sharedPreferences!!.contains(usernameKey)){
            binding.inputLayoutUsername.editText?.setText(sharedPreferences!!.getString(usernameKey,""))
        }
        if(sharedPreferences!!.contains(passwordKey)){
            binding.inputLayoutPassword.editText?.setText(sharedPreferences!!.getString(passwordKey,""))
        }

        //register button
        binding.registerNavBtn.setOnClickListener(View.OnClickListener {
            val moveRegister = Intent(this@LoginActivity, Register::class.java)
            startActivity(moveRegister)
        })

        //checking bundle from register activity
        if(intent.hasExtra("register")){
            getBundle()
        }

        //Validate
        binding.inputLayoutUsername.editText?.doAfterTextChanged{
            if (binding.inputLayoutUsername.editText?.text.toString().isEmpty()) {
                binding.inputLayoutUsername.setError("Required")
            }else{
                binding.inputLayoutUsername.setError(null)
            }
        }
        binding.inputLayoutPassword.editText?.doAfterTextChanged{
            if (binding.inputLayoutPassword.editText?.text.toString().isEmpty()) {
                binding.inputLayoutPassword.setError("Required")
            }else{
                binding.inputLayoutPassword.setError(null)
            }
        }

        //Login
        binding.btnLogin.setOnClickListener(View.OnClickListener {
            var loginCheck = true
            val username: String = binding.inputLayoutUsername.editText?.text.toString()
            val password: String = binding.inputLayoutPassword.editText?.text.toString()

            if (username.isEmpty()) {
                binding.inputLayoutUsername.setError("Required")
                loginCheck = false
            }

            if (password.isEmpty()) {
                binding.inputLayoutPassword.setError("Required")
                loginCheck = false
            }

            if(!loginCheck)return@OnClickListener

            CoroutineScope(Dispatchers.Main).launch {
                val users = db.userDao().getUsernamePassword(username, password)
                if (users != null){
                    //insert to Preferences for next login
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(usernameKey,username)
                    editor.putString(passwordKey, password)
                    editor.apply()

                    sendNotification1(username)
                    sendNotification2(username)

                    val moveHome = Intent(this@LoginActivity, Home::class.java)
                    val mBundleL = Bundle()
                    mBundleL.putInt("user_id", users.id)
                    moveHome.putExtra("login", mBundleL)
                    startActivity(moveHome)
                }else{
                    Snackbar.make(layoutMain, "User not found!", Snackbar.LENGTH_LONG).show()
                }
            }
            return@OnClickListener
        })
    }

    fun getBundle(){
        mBundle = intent.getBundleExtra("register")!!
        if(!mBundle.isEmpty){
            binding.inputLayoutUsername.editText?.setText(mBundle.getString("username")!!)
            binding.inputLayoutPassword.editText?.setText("")
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

            val channel2 = NotificationChannel(CHANNEL_ID_2,name,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }
    }

    private fun sendNotification1(vUsername: String){
        val intent : Intent = Intent()
        val mBundleL = Bundle()
        intent.putExtra("login", mBundleL)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", vUsername)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle(vUsername)
            .setContentText("Berhasil Login")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.long_text))
                .setBigContentTitle("Ucapan Terima Kasih")
                .setSummaryText("BigText"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(false)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)


        with(NotificationManagerCompat.from(this)){
            notify(notification1, builder.build())
        }
    }

    private fun sendNotification2(vUsername: String){
        val intent : Intent = Intent()
        val mBundleL = Bundle()
        intent.putExtra("login", mBundleL)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", vUsername)
        val actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setSmallIcon(R.drawable.ic_baseline_check_circle_24)
            .setContentTitle(vUsername)
            .setContentText("Berhasil Login")
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("Ricky Junaidi")
                .addLine("Enrique Trisfan")
                .addLine("Bonifasius Adhi Renalge Dewanto")
                .setBigContentTitle("Developers")
                .setSummaryText("InBox"))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setColor(Color.RED)
            .setAutoCancel(true)
            .setOnlyAlertOnce(false)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)


        with(NotificationManagerCompat.from(this)){
            notify(notification2, builder.build())
        }
    }
}