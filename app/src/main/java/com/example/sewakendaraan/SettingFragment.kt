package com.example.sewakendaraan

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.entity.SettingItem
import com.example.sewakendaraan.room.userRoom.UserDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingFragment : Fragment() {
    var vId: Int = 0
    private var vUsername: String = "admin"
    private var vEmail: String = "admin@email.com"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home
        val db by lazy { UserDB(context) }

        val layoutManager = LinearLayoutManager(context)
        val rvSetting : RecyclerView = context.findViewById(R.id.rvSetting)
        val adapter = RVItemSetting(SettingItem.listSettingItem)

        rvSetting.layoutManager = layoutManager
        rvSetting.hasFixedSize()
        rvSetting.adapter = adapter
        rvSetting.addItemDecoration(
            DividerItemDecoration(
                rvSetting.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        val logoutBtn : Button = context.findViewById(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure want to logout?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface: DialogInterface, i: Int){
                        val logout = Intent(context, LoginActivity::class.java)
                        startActivity(logout)
                        context.finishAndRemoveTask()
                    }
                }).show()
        }

        val tvUsername : TextView = context.findViewById(R.id.tvUsername)
        val tvEmail: TextView = context.findViewById(R.id.tvEmail)
        vId = context.vId
        CoroutineScope(Dispatchers.Main).launch {
            val users = db.userDao().getUser(vId)
            if (users != null) {
                vUsername = users.username
                vEmail = users.email
                tvUsername.text = vUsername
                tvEmail.text = vEmail
            }
        }

        val editIcon : ImageView = context.findViewById(R.id.editIcon)
        editIcon.setOnClickListener{
            replaceFragment(ProfileFragment())
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val args = Bundle()
        fragment.arguments = args
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}