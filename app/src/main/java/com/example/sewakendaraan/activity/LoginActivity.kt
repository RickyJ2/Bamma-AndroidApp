package com.example.sewakendaraan.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.activity.home.HomeActivity
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.ActivityLoginBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.idKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.loginPrefKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.passwordKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.usernameKey
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var mLoginViewModel: LoginViewModel
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notification1 = 101
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notification2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"

        //databinding
        mLoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        DataBindingUtil.setContentView<ActivityLoginBinding>(
            this, R.layout.activity_login
        ).apply{
            this.lifecycleOwner = this@LoginActivity
            this.viewmodel = mLoginViewModel
        }

        createNotificationChannel()

        //SharedPreferences akun login terakhir kali atau dari register
        loadPreferences()
        //Observe Msg
        mLoginViewModel.msg.observe(this@LoginActivity){
            if(mLoginViewModel.msg.value != ""){
                Toast.makeText(this@LoginActivity, mLoginViewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun moveRegister(view: View){
        if(view == findViewById(R.id.registerNavBtn)){
            val moveRegisterActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegisterActivity)
        }
    }
    fun moveReqResetPassword(view: View){
        if(view == findViewById(R.id.forgotPasswordBtn)){
            val moveReqResetPasswordActivity = Intent(this@LoginActivity, ReqResetPasswordActivity::class.java)
            startActivity(moveReqResetPasswordActivity)
        }
    }
    fun login(view: View){
        if(view == findViewById(R.id.btnLogin)){
            mLoginViewModel.setProgressBar(View.VISIBLE)
            mLoginViewModel.login()
            mLoginViewModel.code.observe(this@LoginActivity){
                if(it == 200){
                    savePreferences(
                        mLoginViewModel.loginForm.value!!.username,
                        mLoginViewModel.loginForm.value!!.password,
                        mLoginViewModel.readLoginData.value!!.id)
                    sendNotification1(mLoginViewModel.loginForm.value!!.username)
                    sendNotification2(mLoginViewModel.loginForm.value!!.username)
                    val moveHomeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(moveHomeActivity)
                }
                if(it != null){
                    mLoginViewModel.setProgressBar(View.INVISIBLE)
                }
            }
        }
    }
    private fun loadPreferences(){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        if(spLogin.contains(usernameKey)){
            spLogin.getString(usernameKey, "")?.let { mLoginViewModel.setUsernameForm(it) }
        }
        if(spLogin.contains(passwordKey)){
            spLogin.getString(passwordKey,"")?.let { mLoginViewModel.setPasswordForm(it) }
        }
    }
    private fun savePreferences(username: String, password: String, id: Int){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spLogin.edit()
        editor.putString(usernameKey,username)
        editor.putString(passwordKey, password)
        editor.putInt(idKey, id)
        editor.apply()
    }
    private fun createNotificationChannel() {
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
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification1(vUsername: String){
        val intent = Intent()
        val mBundleL = Bundle()
        intent.putExtra("login", mBundleL)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
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
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotification2(vUsername: String){
        val intent = Intent()
        val mBundleL = Bundle()
        intent.putExtra("login", mBundleL)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
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