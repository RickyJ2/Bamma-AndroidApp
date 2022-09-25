package com.example.sewakendaraan

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sewakendaraan.databinding.ActivityHomeBinding
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.room.User
import com.example.sewakendaraan.room.UserDB
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {
    val db by lazy { UserDB(this) }
    lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        setupListener()
        setupRecyclerView()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.settings -> {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this@Home)
                    builder.setMessage("Are you sure want to logout?")
                        .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                            override fun onClick(dialogInterface: DialogInterface, i: Int){
                                finishAndRemoveTask()
                            }
                        }).show()
                }

                else -> {

                }

            }
            true
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

    private fun setupRecyclerView(){
        userAdapter = UserAdapter(arrayListOf(), object: UserAdapter.OnAdapterListener{
            override fun onClick(user: User) {
                intentEdit(user.id, Constant.TYPE_READ)
            }

            override fun onUpdate(user: User) {
                intentEdit(user.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(user: User) {
                deleteDialog(user)
            }
        })
        list_user.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = userAdapter
        }
    }
    private fun deleteDialog(user: User){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are you sure to delete this data from ${user.username}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener{
                    dialogInterface, i ->
                dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener{
                    dialogInterface, i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteUser(user)
                    loadData()
                }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            val users = db.userDao().getUsers()
            Log.d("MainActivity", "dbResponse: $users")
            withContext(Dispatchers.Main){
                userAdapter.setData(users)
            }
        }
    }
    fun setupListener(){
        button_create.setOnClickListener{
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(userId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id", userId)
                .putExtra("intent_type", intentType)
        )
    }
}