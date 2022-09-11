package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.entity.Kendaraan

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter: RVKendaraanAdapter = RVKendaraanAdapter((Kendaraan.listOfKendaraan))

        val rvKendaraan : RecyclerView = view.findViewById(R.id.rvKendaraan)

        rvKendaraan.layoutManager = layoutManager
        rvKendaraan.setHasFixedSize(true)
        rvKendaraan.adapter = adapter
    }
}
