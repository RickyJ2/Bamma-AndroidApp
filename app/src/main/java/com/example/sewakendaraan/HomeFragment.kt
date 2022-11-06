package com.example.sewakendaraan

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import com.example.sewakendaraan.room.kendaraanRoom.KendaraanDB
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.room.userRoom.UserDB
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    lateinit var vUsername: String
    var vId: Int = 0
    val args = Bundle()
    lateinit var kendaraanAdapter: RVKendaraanAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home
        val db by lazy { UserDB(context) }
        val tvWelcome: TextView = context.findViewById(R.id.tvWelcome) as TextView
        vId = context.vId

        CoroutineScope(Dispatchers.Main).launch {
            val users = db.userDao().getUser(vId)
            if (users != null) {
                vUsername = users.username
                tvWelcome.text = "Welcome, $vUsername!"
            }
        }
        val imageMap: ImageButton = context.findViewById(R.id.imageView3)
        imageMap.setOnClickListener{
            replaceFragment(MapLocationFragment())
        }

        setupRecyclerView()
    }
    private fun setupRecyclerView(){
        val context = context as Home
        kendaraanAdapter = RVKendaraanAdapter(arrayListOf(), object : RVKendaraanAdapter.OnAdapterListener{
            override fun onClick(kendaraan: Kendaraan){
                argEdit(kendaraan.id, Constant.TYPE_READ)
            }
            override fun onUpdate(kendaraan: Kendaraan){
                argEdit(kendaraan.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(kendaraan: Kendaraan) {
                deleteDialog(kendaraan)
            }
        })
        rvKendaraan.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = kendaraanAdapter
        }
    }

    private fun deleteDialog(kendaraan: Kendaraan){
        val context = context as Home
        val db by lazy { KendaraanDB(context) }
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are you sure to delete this data from ${kendaraan.jenisKendaraan}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener{
                dialogInterface, i ->
                    dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener{
                dialogInterface, i ->
                    dialogInterface.dismiss()
                    CoroutineScope(Dispatchers.IO).launch {
                        db.kendaraanDao().deleteKendaraan(kendaraan)
                        loadData()
                    }
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        val context = context as Home
        val db by lazy { KendaraanDB(context) }
        CoroutineScope(Dispatchers.IO).launch {
            val kendaraan = db.kendaraanDao().getKendaraan()
            withContext(Dispatchers.Main){
                kendaraanAdapter.setData(kendaraan)
            }
        }
    }

    fun argEdit(kendaraanId: Int, fragmentType: Int){
        args.putInt("arg_type", fragmentType)
        replaceFragment(EditKendaraanFragment())
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        fragment.arguments = args
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}


