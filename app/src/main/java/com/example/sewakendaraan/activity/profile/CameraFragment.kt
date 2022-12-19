@file:Suppress("DEPRECATION")

package com.example.sewakendaraan.activity.profile

import android.hardware.Camera
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.sewakendaraan.R
import com.example.sewakendaraan.databinding.FragmentCameraBinding

class CameraFragment : Fragment() {
    private var binding: FragmentCameraBinding? = null
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    private var currentCameraId: Int = Camera.CameraInfo.CAMERA_FACING_BACK

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try{
            mCamera = Camera.open()
        }catch (e: Exception){
            Toast.makeText((activity as ProfileActivity),"Failed to get Camera", Toast.LENGTH_SHORT).show()
        }
        if(mCamera != null){
            mCameraView = CameraView((activity as ProfileActivity), mCamera!!)
            val cameraView = binding?.FLCamera as FrameLayout
            cameraView.addView(mCameraView)
        }

        binding?.closeBtn?.setOnClickListener{
            try {
                mCamera?.stopPreview()
            } catch (e: Exception){
                e.printStackTrace()
            }
            mCamera?.release()
            replaceFragment(ProfileFragment())
        }
        binding?.cameraSwitchBtn?.setOnClickListener{
            changeCamera()
        }
    }
    private fun changeCamera(){
        try {
            mCamera?.stopPreview()
        } catch (e: Exception){
            e.printStackTrace()
        }
        mCamera?.release()
        currentCameraId = if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            Camera.CameraInfo.CAMERA_FACING_FRONT
        }else{
            Camera.CameraInfo.CAMERA_FACING_BACK
        }

        try{
            mCamera = Camera.open(currentCameraId)
        }catch (e: Exception){
            Toast.makeText((activity as ProfileActivity), "Failed to get camera", Toast.LENGTH_SHORT).show()
        }
        if(mCamera != null){
            mCameraView = CameraView((activity as ProfileActivity), mCamera!!)
            val cameraView = binding?.FLCamera as FrameLayout
            cameraView.addView(mCameraView)
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val fragmentManager = (activity as ProfileActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}