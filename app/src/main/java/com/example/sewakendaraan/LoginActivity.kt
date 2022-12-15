package com.example.sewakendaraan

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.databinding.ActivityLoginBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.idKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.loginPrefKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.passwordKey
import com.example.sewakendaraan.entity.sharedPreferencesKey.Companion.usernameKey
import com.example.sewakendaraan.notification.NotificationReceiver
import com.example.sewakendaraan.viewModel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.runBlocking

class LoginActivity : AppCompatActivity() {
    private lateinit var  binding: ActivityLoginBinding
    private lateinit var mUserViewModel: UserViewModel

    private val CHANNEL_ID_1 = "channel_notification_01"
    private val notification1 = 101
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notification2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "Login"

        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        createNotificationChannel()

        //SharedPreferences akun login terakhir kali atau dari register
        loadPreferences()

        //register button
        binding.registerNavBtn.setOnClickListener(View.OnClickListener {
            val moveRegisterActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveRegisterActivity)
        })

        //Validate
        validation()
        //Login
        binding.btnLogin.setOnClickListener(View.OnClickListener {
            val username: String = binding.inputLayoutUsername.editText?.text.toString()
            val password: String = binding.inputLayoutPassword.editText?.text.toString()

            if(!inputcheck()){
                return@OnClickListener
            }else{
                Log.d("Login", "Start")
                mUserViewModel.setLogin(username, password)
                mUserViewModel.loadState.observe(this@LoginActivity) {
                    if(it == "SUCCESS"){
                        if(mUserViewModel.readLoginData?.value != null){
                            savePreferences(username, password, mUserViewModel.readLoginData.value!!.id)
                            sendNotification1(username)
                            sendNotification2(username)
                            val moveHome = Intent(this@LoginActivity, Home::class.java)
                            startActivity(moveHome)
                        }else{
                            Snackbar.make(view, "User not found!", Snackbar.LENGTH_LONG).show()
                        }
                        Log.d("Login", "Done")
                    }else{
                        Log.d("Login", it)
                    }
                }
            }
            return@OnClickListener
        })
    }
    private fun validation(){
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
    }
    private fun inputcheck(): Boolean{
        return (
            binding.inputLayoutUsername.error == null &&
            binding.inputLayoutPassword.error == null
            )
    }
    private fun loadPreferences(){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        if(spLogin!!.contains(usernameKey)){
            binding.inputLayoutUsername.editText?.setText(spLogin!!.getString(usernameKey,""))
        }
        if(spLogin!!.contains(passwordKey)){
            binding.inputLayoutPassword.editText?.setText(spLogin!!.getString(passwordKey,""))
        }
    }
    private fun savePreferences(username: String, password: String, id: Int){
        val spLogin: SharedPreferences = getSharedPreferences(loginPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = spLogin!!.edit()
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