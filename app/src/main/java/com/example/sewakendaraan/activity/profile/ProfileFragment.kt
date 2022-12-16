package com.example.sewakendaraan.activity.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

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