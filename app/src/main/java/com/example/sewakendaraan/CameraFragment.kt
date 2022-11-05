package com.example.sewakendaraan

import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.sewakendaraan.databinding.FragmentCameraBinding
import com.example.sewakendaraan.databinding.FragmentEditKendaraanBinding

class CameraFragment : Fragment() {
    private var binding: FragmentCameraBinding? = null
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    private var currentCameraId: Int = Camera.CameraInfo.CAMERA_FACING_BACK

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home

        try{
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if(mCamera != null){
            mCameraView = CameraView(context, mCamera!!)
            val camera_view = binding?.FLCamera as FrameLayout
            camera_view.addView(mCameraView)
        }

        binding?.closeBtn?.setOnClickListener{
            try {
                mCamera?.stopPreview()
            } catch (e: Exception){
                e.printStackTrace();
            }
            mCamera?.release()
            replaceFragment(ProfileFragment())
        }
        binding?.cameraSwitchBtn?.setOnClickListener{
            changeCamera()
        }
    }
    fun changeCamera(){
        val context = context as Home
        try {
            mCamera?.stopPreview()
        } catch (e: Exception){
            e.printStackTrace();
        }
        mCamera?.release()
        if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT
        }else{
            currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK
        }

        try{
            mCamera = Camera.open(currentCameraId)
        }catch (e: Exception){
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if(mCamera != null){
            mCameraView = CameraView(context, mCamera!!)
            val camera_view = binding?.FLCamera as FrameLayout
            camera_view.addView(mCameraView)
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