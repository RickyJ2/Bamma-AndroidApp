package com.example.sewakendaraan.activity.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.rv.RVItemSetting
import com.example.sewakendaraan.activity.LoginActivity
import com.example.sewakendaraan.databinding.FragmentSettingBinding
import com.example.sewakendaraan.entity.SettingItem
import com.example.sewakendaraan.entity.sharedPreferencesKey


class SettingFragment : Fragment() {
    private var binding: FragmentSettingBinding? = null
    private lateinit var rvSetting: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentSettingBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply{
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as HomeActivity).mHomeViewModel
            settingFragment = this@SettingFragment
        }

        val layoutManager = LinearLayoutManager(context)
        rvSetting = (activity as HomeActivity).findViewById(R.id.rvSetting)
        val adapter = RVItemSetting(SettingItem.listSettingItem){
            when(it.title){
                "Tentang kami" -> (activity as HomeActivity).moveTentangkami()
                "Kritik dan Saran" -> (activity as HomeActivity).moveKritikSaran()
                else -> {
                }
            }
        }

        rvSetting.layoutManager = layoutManager
        rvSetting.adapter = adapter
        rvSetting.addItemDecoration(
            DividerItemDecoration(
                rvSetting.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
    fun logout(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity as HomeActivity)
        builder.apply{
            setMessage("Are you sure want to logout?")
            setNegativeButton("NO") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setPositiveButton("YES") { _, _ ->
                val logout = Intent((activity as HomeActivity), LoginActivity::class.java)

                val spLogin: SharedPreferences = (activity as HomeActivity).getSharedPreferences(
                    sharedPreferencesKey.loginPrefKey,
                    Context.MODE_PRIVATE
                )
                val editorLogin: SharedPreferences.Editor = spLogin.edit()
                editorLogin.putInt(sharedPreferencesKey.idKey, -1)
                editorLogin.apply()

                startActivity(logout)
                (activity as HomeActivity).finishAndRemoveTask()
            }
        }
        builder.show()
    }
    fun moveProfile(){
        (activity as HomeActivity).moveProfile()
    }
}