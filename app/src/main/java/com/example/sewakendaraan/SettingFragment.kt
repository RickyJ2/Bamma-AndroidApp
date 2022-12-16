package com.example.sewakendaraan

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.example.sewakendaraan.activity.LoginActivity
import com.example.sewakendaraan.activity.profile.EditProfileFragment
import com.example.sewakendaraan.databinding.FragmentSettingBinding
import com.example.sewakendaraan.entity.SettingItem
import com.example.sewakendaraan.entity.sharedPreferencesKey


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home

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

        binding.logoutBtn.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure want to logout?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface: DialogInterface, i: Int){
                        val logout = Intent(context, LoginActivity::class.java)

                        val spLogin: SharedPreferences = context.getSharedPreferences(sharedPreferencesKey.loginPrefKey, Context.MODE_PRIVATE)
                        val editorLogin: SharedPreferences.Editor = spLogin!!.edit()
                        editorLogin.putInt(sharedPreferencesKey.idKey, -1)
                        editorLogin.apply()

                        startActivity(logout)
                        context.finishAndRemoveTask()
                    }
                }).show()
        }

        binding.tvUsername.text = context.mHomeViewModel.readLoginData?.value?.username.toString()
        binding.tvEmail.text = context.mHomeViewModel.readLoginData?.value?.email.toString()

        binding.editIcon.setOnClickListener{
            (activity as Home).moveProfile()
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