package com.example.sewakendaraan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.room.User
import com.example.sewakendaraan.room.UserDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy{ UserDB(this) }
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE->{
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ->{
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getUser()
            }
            Constant.TYPE_UPDATE->{
                button_save.visibility = View.GONE
                getUser()
            }
        }
    }
    private fun setupListener(){
        button_save.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                with(db){
                    db.userDao().addUser(
                        User(0, edit_title.text.toString(), edit_user.text.toString())
                    )
                    finish()
                }

            }
        }
        button_update.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().updateUser(
                    User(userId, edit_title.text.toString(),edit_user.text.toString())
                )
                finish()
            }
        }

    }

    fun getUser() {
        userId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val users = db.userDao().getUser(userId)
            edit_title.setText(users[0].username)
            edit_user.setText(users[0].user)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}