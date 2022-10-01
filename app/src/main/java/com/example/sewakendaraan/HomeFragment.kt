package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.kendaraanRoom.Kendaraan
import org.w3c.dom.Text

class HomeFragment : Fragment() {
    var vUsername: String = "admin"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home
        val tvWelcome: TextView = context.findViewById(R.id.tvWelcome) as TextView

        val args = arguments
        vUsername = args!!.getString("username").toString()
        tvWelcome.text = "Welcome, $vUsername!"
       /* val layoutManager = LinearLayoutManager(context)
        val adapter: RVKendaraanAdapter = RVKendaraanAdapter((Kendaraan.listOfKendaraan))

        val rvKendaraan : RecyclerView = view.findViewById(R.id.rvKendaraan)

        rvKendaraan.layoutManager = layoutManager
        rvKendaraan.setHasFixedSize(true)
        rvKendaraan.adapter = adapter*/
    }
}
