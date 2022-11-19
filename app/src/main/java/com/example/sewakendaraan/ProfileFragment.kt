package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.databinding.FragmentHomeBinding
import com.example.sewakendaraan.databinding.FragmentProfileBinding
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDB
import com.example.sewakendaraan.room.userRoom.UserViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editProfilePictureButton.setOnClickListener{
            replaceFragment(CameraFragment())
        }

        binding.inputLayoutUsername.editText?.setText(mUserViewModel.readLoginData?.value?.username)
        binding.inputLayoutEmail.editText?.setText(mUserViewModel.readLoginData?.value?.email)
        binding.inputLayoutHandphone.editText?.setText(mUserViewModel.readLoginData?.value?.username)
        binding.inputLayoutDateOfBirth.editText?.setText(mUserViewModel.readLoginData?.value?.dateofbirth)

        binding.inputLayoutDateOfBirth.editText?.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus){
                datePicker()
            }
        }
        binding.inputLayoutDateOfBirth.editText?.setOnClickListener{
            datePicker()
        }

        binding.updateProfileBtn.setOnClickListener{
            updateUser()
            replaceFragment(SettingFragment())
        }
    }
    private fun datePicker(){
        val context = context as Home
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        inputLayoutDateOfBirth.setEndIconOnClickListener {
            datePicker.show(context.supportFragmentManager, datePicker.tag)
        }

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            inputLayoutDateOfBirth.editText?.setText(sdf.format(datePicker.selection))
        }
    }
    private fun updateUser(){
        val username: String = binding.inputLayoutUsername.editText?.text.toString()
        val email: String = binding.inputLayoutEmail.editText?.text.toString()
        val handphone: String = binding.inputLayoutHandphone.editText?.text.toString()
        val dateOfBirth: String = binding.inputLayoutDateOfBirth.editText?.text.toString()

        val user = User(
            0,
            username,
            email,
            mUserViewModel.readLoginData?.value?.password.toString(),
            handphone,
            dateOfBirth
        )
        mUserViewModel.updateUser(user)
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}