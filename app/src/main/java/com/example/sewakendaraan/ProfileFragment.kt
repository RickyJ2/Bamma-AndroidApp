package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.sewakendaraan.room.userRoom.User
import com.example.sewakendaraan.room.userRoom.UserDB
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var vId: Int = 0
    private var vUsername: String = "admin"
    private var vEmail: String = "admin@email.com"
    private var vPassword: String = "admin"
    private var vHandphone: String = "0812345678"
    private var vDateOfBirth: String = "01/01/2001"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home

        val editProfileImageButton: ImageView = context.findViewById(R.id.editProfilePictureButton)
        editProfileImageButton.setOnClickListener {
            replaceFragment(CameraFragment())
        }

        val db by lazy { UserDB(context) }

        val inputLayoutUsername: TextInputLayout = context.findViewById(R.id.inputLayoutUsername)
        val inputLayoutEmail: TextInputLayout = context.findViewById(R.id.inputLayoutEmail)
        val inputLayoutHandphone: TextInputLayout = context.findViewById(R.id.inputLayoutHandphone)
        val inputLayoutDateOfBirth: TextInputLayout =
            context.findViewById(R.id.inputLayoutDateOfBirth)
        val updateProfileBtn: Button = context.findViewById(R.id.updateProfileBtn)

        vId = context.vId

        CoroutineScope(Dispatchers.Main).launch {
            val users = db.userDao().getUser(vId)
            if (users != null) {
                vUsername = users.username
                vEmail = users.email
                vPassword = users.password
                vHandphone = users.handphone
                vDateOfBirth = users.dateofbirth
                inputLayoutUsername.editText?.setText(vUsername)
                inputLayoutEmail.editText?.setText(vEmail)
                inputLayoutHandphone.editText?.setText(vHandphone)
                inputLayoutDateOfBirth.editText?.setText(vDateOfBirth)
            }
        }

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


        updateProfileBtn.setOnClickListener {
            vUsername = inputLayoutUsername.editText?.text.toString()
            vEmail = inputLayoutEmail.editText?.text.toString()
            vDateOfBirth = inputLayoutDateOfBirth.editText?.text.toString()
            vHandphone = inputLayoutHandphone.editText?.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().updateUser(
                    User(vId, vEmail, vUsername, vPassword, vDateOfBirth, vHandphone)
                )
            }
            replaceFragment(SettingFragment())
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}