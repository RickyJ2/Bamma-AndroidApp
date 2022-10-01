package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sewakendaraan.kendaraanRoom.Kendaraan
import com.example.sewakendaraan.kendaraanRoom.KendaraanDB
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.room.UserDB
import kotlinx.android.synthetic.main.fragment_edit_kendaraan.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditKendaraanFragment : Fragment() {

    val db by lazy { activity?.let { KendaraanDB(it) } }
    private var kendaraanId: Int = 0
    var vId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_kendaraan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val argsType = args!!.getInt("arg_type")
        kendaraanId = args!!.getInt("arg_id")
        vId = args!!.getInt("user_id")

        setupView(argsType)
        setupListener()
    }

    fun setupView(argsType: Int){
        when(argsType){
            Constant.TYPE_CREATE ->{
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getKendaraan()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getKendaraan()
            }
        }
    }
    private fun setupListener(){
        val context = context as Home
        val db by lazy { KendaraanDB(context) }
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.kendaraanDao().addKendaraan(
                    Kendaraan(0,edit_namaPemilik.text.toString(),
                        edit_jenisKendaraan.text.toString())
                )
                replaceFragment(HomeFragment())
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.kendaraanDao().updateKendaraan(
                    Kendaraan(kendaraanId, edit_namaPemilik.text.toString(),
                        edit_jenisKendaraan.text.toString())
                )
                replaceFragment(HomeFragment())
            }
        }
    }
    fun getKendaraan(){
        val context = context as Home
        val db by lazy { KendaraanDB(context) }
        CoroutineScope(Dispatchers.Main).launch {
            val kendaraan = db.kendaraanDao().getKendaraan(kendaraanId)
            edit_namaPemilik.setText(kendaraan.namaPemilik)
            edit_jenisKendaraan.setText(kendaraan.jenisKendaraan)
        }
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val args = Bundle()
        args.putInt("user_id", vId)
        fragment.arguments = args
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}