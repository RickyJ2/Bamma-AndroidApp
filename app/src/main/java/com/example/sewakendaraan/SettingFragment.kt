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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.databinding.FragmentProfileBinding
import com.example.sewakendaraan.databinding.FragmentSettingBinding
import com.example.sewakendaraan.entity.SettingItem
import com.example.sewakendaraan.room.userRoom.UserDB
import com.example.sewakendaraan.room.userRoom.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
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
                        startActivity(logout)
                        context.finishAndRemoveTask()
                    }
                }).show()
        }

        binding.tvUsername.text = mUserViewModel.readLoginData?.value?.username.toString()
        binding.tvEmail.text = mUserViewModel.readLoginData?.value?.email.toString()

        binding.editIcon.setOnClickListener{
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