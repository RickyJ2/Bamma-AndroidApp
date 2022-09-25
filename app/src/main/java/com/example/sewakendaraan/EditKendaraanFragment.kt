package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sewakendaraan.kendaraanRoom.KendaraanDB
import com.example.sewakendaraan.room.Constant
import kotlinx.android.synthetic.main.fragment_edit_kendaraan.*

class EditKendaraanFragment : Fragment() {

    val db by lazy { activity?.let { KendaraanDB(it) } }
    private var kendaraanId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setupView()
        //setupListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_kendaraan, container, false)
    }


}