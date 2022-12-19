package com.example.sewakendaraan.activity.pemesanan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.DaftarMobil
import com.example.sewakendaraan.databinding.FragmentSelectCarBinding
import com.example.sewakendaraan.rv.RVItemDaftarMobilSmall

class SelectCarFragment : Fragment() {
    private var binding: FragmentSelectCarBinding? = null
    private lateinit var rvKendaraan: RecyclerView
    private lateinit var adapter: RVItemDaftarMobilSmall

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentSelectCarBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as PemesananActivity).mPemesananViewModel
            selectCarFragment = this@SelectCarFragment
        }

        val layoutManager = LinearLayoutManager(context)
        rvKendaraan = (activity as PemesananActivity).findViewById(R.id.rvKendaraan)
        adapter = RVItemDaftarMobilSmall(
            (activity as PemesananActivity).mPemesananViewModel.daftarMobilList.value as ArrayList<DaftarMobil>
        ){
            movePesan(it.id)
        }

        rvKendaraan.layoutManager = layoutManager
        rvKendaraan.adapter = adapter

    }
    fun moveCancel(){
        val fragmentManager = (activity as PemesananActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, PemesananFragment())
        fragmentTransaction.commit()
    }
    private fun movePesan(id: Int){
        (activity as PemesananActivity).movePesan(id)
    }
}