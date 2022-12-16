package com.example.sewakendaraan.activity.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.FragmentEditProfileBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null
    private lateinit var inputLayoutDateOfBirth: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as ProfileActivity).mProfileUserViewModel
            editProfileFragment = this@EditProfileFragment
        }

        (activity as ProfileActivity).mProfileUserViewModel.setupEditForm()

        inputLayoutDateOfBirth = (activity as ProfileActivity).findViewById(R.id.inputLayoutDateOfBirth)
        inputLayoutDateOfBirth.editText?.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus){
                datePicker()
            }
        }
    }
    fun datePicker(){
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        if(!datePicker.isVisible)
            datePicker.show((activity as ProfileActivity).supportFragmentManager, datePicker.tag)

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            (activity as ProfileActivity).mProfileUserViewModel.setDatePicker(sdf.format(datePicker.selection))
        }
    }
    fun updateUser(){
        (activity as ProfileActivity).mProfileUserViewModel.setProgressBar(View.VISIBLE)
        (activity as ProfileActivity).mProfileUserViewModel.updateUser()
        (activity as ProfileActivity).mProfileUserViewModel.code.observe(this@EditProfileFragment){
            if(it == 200){
                moveProfile()
            }
            if(it != null){
                (activity as ProfileActivity).mProfileUserViewModel.setProgressBar(View.INVISIBLE)
            }
        }
    }
    fun moveProfile(){
        val fragmentManager = (activity as ProfileActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,ProfileFragment())
        fragmentTransaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}