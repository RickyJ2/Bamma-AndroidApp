package com.example.sewakendaraan.activity.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.rv.RVItemDaftarMobil
import com.example.sewakendaraan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var rvKendaraan: RecyclerView
    private lateinit var adapter: RVItemDaftarMobil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as HomeActivity).mHomeViewModel
            homeFragment = this@HomeFragment
        }

        val layoutManager = LinearLayoutManager(context)
        rvKendaraan = (activity as HomeActivity).findViewById(R.id.rvKendaraan)
        adapter = RVItemDaftarMobil(arrayListOf()){
            (activity as HomeActivity).moveKendaraanDetail(it.id)
        }

        rvKendaraan.layoutManager = layoutManager
        rvKendaraan.adapter = adapter
        rvKendaraan.addItemDecoration(
            DividerItemDecoration(
                rvKendaraan.context,
                DividerItemDecoration.VERTICAL
            )
        )

        (activity as HomeActivity).mHomeViewModel.daftarMobil.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
    fun moveProfile(){
        (activity as HomeActivity).moveProfile()
    }
    fun movePemesananList(){
        (activity as HomeActivity).movePemesananList()
    }
}


