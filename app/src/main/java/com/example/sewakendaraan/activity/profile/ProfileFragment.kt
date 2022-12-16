package com.example.sewakendaraan.activity.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.FragmentProfileBinding
import com.google.android.material.imageview.ShapeableImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private lateinit var profileImageView: ShapeableImageView
    private var imageUri = MutableLiveData<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as ProfileActivity).mProfileUserViewModel
            profileFragment = this@ProfileFragment
        }
        (activity as ProfileActivity).loginSetup()

        profileImageView = (activity as ProfileActivity).findViewById(R.id.profileFoto)
        (activity as ProfileActivity).mProfileUserViewModel.readLoginData.observe(viewLifecycleOwner){
            if(it != null){
                Glide.with(profileImageView)
                    .load(it.image)
                    .into(profileImageView)
            }
        }
    }
    fun pickImage(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)

        imageUri.observe(viewLifecycleOwner){
            if(it != null){
                val requestBody = RequestBody.create(MediaType.parse("multipart"), File(it.path.toString()))
                val image = MultipartBody.Part.createFormData("imagename", File(it.path.toString()).name, requestBody)
                updateProfile(image)
            }
        }
    }
    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        if(result.resultCode == Activity.RESULT_OK){
            val data = result.data
            imageUri.value = data!!.data
        }else{
            Toast.makeText(requireContext(), "Batal..", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateProfile(image: MultipartBody.Part){
        Log.d("profile", "updateing")
        (activity as ProfileActivity).mProfileUserViewModel.setProgressBar(View.VISIBLE)
        (activity as ProfileActivity).mProfileUserViewModel.updateProfile(image)
        (activity as ProfileActivity).mProfileUserViewModel.code.observe(viewLifecycleOwner){
            if(it != null){
                (activity as ProfileActivity).mProfileUserViewModel.setProgressBar(View.INVISIBLE)
            }
            Log.d("profile", it.toString())
        }
    }
    fun moveUpdateProfile(){
        val fragmentManager = (activity as ProfileActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,EditProfileFragment())
        fragmentTransaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}