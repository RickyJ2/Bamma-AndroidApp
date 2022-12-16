package com.example.sewakendaraan.activity.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.R
import com.example.sewakendaraan.activity.LoginActivity
import com.example.sewakendaraan.databinding.ActivityProfileBinding
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.viewModel.ProfileUserViewModel

class ProfileActivity : AppCompatActivity() {
    lateinit var mProfileUserViewModel: ProfileUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mProfileUserViewModel = ViewModelProvider(this)[ProfileUserViewModel::class.java]
        DataBindingUtil.setContentView<ActivityProfileBinding>(
            this, R.layout.activity_profile
        ).apply{
            this.lifecycleOwner = this@ProfileActivity
            this.viewmodel = mProfileUserViewModel
        }
        replaceFragment(ProfileFragment())

        //Observe Msg
        mProfileUserViewModel.msg.observe(this@ProfileActivity){
            if(mProfileUserViewModel.msg.value != ""){
                Toast.makeText(this@ProfileActivity, mProfileUserViewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun loginSetup(){
        mProfileUserViewModel.setProgressBar(View.VISIBLE)
        val spLogin: SharedPreferences = getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
        val id = spLogin.getInt(sharedPreferencesKey.idKey, -1)
        if(id == -1){
            mProfileUserViewModel.setProgressBar(View.INVISIBLE)
            val moveLogin = Intent(this@ProfileActivity, LoginActivity::class.java)
            Toast.makeText(this@ProfileActivity, "Forced Logout", Toast.LENGTH_SHORT).show()
            startActivity(moveLogin)
        }else{
            mProfileUserViewModel.userData(id)
            mProfileUserViewModel.code.observe(this@ProfileActivity){
                if(it != null){
                    mProfileUserViewModel.setProgressBar(View.INVISIBLE)
                    mProfileUserViewModel.code.removeObservers(this@ProfileActivity)
                }
            }
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}