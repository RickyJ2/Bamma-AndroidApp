package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.kendaraanRoom.KendaraanDB
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.room.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var mBundle: Bundle

    val db by lazy { UserDB(this) }

    lateinit var vUsername: String
    var vId: Int = 0

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notification1 = 101
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notification2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        createNotificationChannel()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if(intent.hasExtra("login")){
            getBundle()
            sendNotification1()
            sendNotification2()
        }
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.background = null
        binding.addFB.setOnClickListener{
            val fragment: Fragment = EditKendaraanFragment()
            val args = Bundle()
            args.putInt("user_id", vId)
            args.putInt("arg_id", 0)
            args.putInt("arg_type", Constant.TYPE_CREATE)
            fragment.arguments = args
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout,fragment)
            fragmentTransaction.commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.settings -> replaceFragment(SettingFragment())
                else -> {

                }
            }
            true
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val args = Bundle()
        args.putInt("user_id", vId)
        fragment.arguments = args
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
    fun getBundle(){
        mBundle = intent.getBundleExtra("login")!!
        if(!mBundle.isEmpty){
            vId = mBundle.getInt("user_id")!!
            vUsername = mBundle.getString("username").toString()
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

    private fun sendNotification1(){
        val intent : Intent = Intent()
        /*val intent : Intent = Intent(this, Home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val mBundleL = Bundle()
        mBundleL.putInt("user_id", vId)
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

    private fun sendNotification2(){
        val intent : Intent = Intent()
        /*val intent : Intent = Intent(this, Home::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val mBundleL = Bundle()
        mBundleL.putInt("user_id", vId)
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