package com.example.sewakendaraan.activity.pemesanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sewakendaraan.R
import com.example.sewakendaraan.api.RClient
import com.example.sewakendaraan.databinding.FragmentPemesananBinding
import com.google.android.material.textfield.TextInputLayout

class PemesananFragment : Fragment() {
    private var binding: FragmentPemesananBinding? = null
    private lateinit var inputLayoutPeminjaman: TextInputLayout
    private lateinit var inputLayoutPengembalian: TextInputLayout
    private lateinit var image: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentPemesananBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as PemesananActivity).mPemesananViewModel
            pemesananFragment = this@PemesananFragment
        }

        //load image
        image = (activity as PemesananActivity).findViewById(R.id.gambarMobil)
        (activity as PemesananActivity).mPemesananViewModel.daftarMobil.observe(viewLifecycleOwner){
            if(it != null){
                Glide.with(this)
                    .load(RClient.imageBaseUrl() + it.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image)
            }
        }

        //datePicker dan TimePicker
        inputLayoutPeminjaman = (activity as PemesananActivity).findViewById(R.id.inputLayoutPeminjaman)
        inputLayoutPeminjaman.editText?.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                datePicker(inputLayoutPeminjaman)
            }
        }
        inputLayoutPengembalian = (activity as PemesananActivity).findViewById(R.id.inputLayoutPengembalian)
        inputLayoutPengembalian.editText?.setOnFocusChangeListener{_, hasFocus ->
            if(hasFocus){
                datePicker(inputLayoutPengembalian)
            }
        }
    }
    fun pesan(){
        (activity as PemesananActivity).pesan()
    }
    fun cancel(){
        (activity as PemesananActivity).cancel()
    }
    fun cancelSelected(){
        (activity as PemesananActivity).cancelSelected()
    }
    fun selectCar(){
        val fragmentManager = (activity as PemesananActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, SelectCarFragment())
        fragmentTransaction.commit()
    }
    fun datePicker(view: View){
        (activity as PemesananActivity).datePicker(view)
    }
}