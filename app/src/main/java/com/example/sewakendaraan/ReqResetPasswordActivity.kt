package com.example.sewakendaraan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.databinding.ActivityReqResetPasswordBinding
import com.example.sewakendaraan.viewModel.ReqResetPasswordViewModel

class ReqResetPasswordActivity : AppCompatActivity() {
    private lateinit var mReqResetPasswordViewModel: ReqResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_req_reset_password)
        title = "Request Reset Password"

        //databinding
        mReqResetPasswordViewModel = ViewModelProvider(this)[ReqResetPasswordViewModel::class.java]
        DataBindingUtil.setContentView<ActivityReqResetPasswordBinding>(
            this, R.layout.activity_req_reset_password
        ).apply {
            this.lifecycleOwner = this@ReqResetPasswordActivity
            this.viewmodel = mReqResetPasswordViewModel
        }
        mReqResetPasswordViewModel.msg.observe(this@ReqResetPasswordActivity){
            if(mReqResetPasswordViewModel.msg.value != ""){
                Toast.makeText(this@ReqResetPasswordActivity, mReqResetPasswordViewModel.msg.value.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun reqResetPassword(view: View){
        if(view == findViewById(R.id.btnReqResetPassword)){
            mReqResetPasswordViewModel.setProgressBar(View.VISIBLE)
            mReqResetPasswordViewModel.reqResetPassword()
            mReqResetPasswordViewModel.code.observe(this@ReqResetPasswordActivity){
                if(it == 200){
                    val moveReqResetPasswordActivity = Intent(this@ReqResetPasswordActivity, LoginActivity::class.java)
                    startActivity(moveReqResetPasswordActivity)
                }
                if(it != null){
                    mReqResetPasswordViewModel.setProgressBar(View.INVISIBLE)
                }
            }
        }
    }
}