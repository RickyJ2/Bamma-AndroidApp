package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.viewModel.DaftarMobilViewModel
import kotlinx.android.synthetic.main.fragment_edit_kendaraan.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class EditKendaraanFragment : Fragment() {

    private var kendaraanId: Int = 0
    lateinit var mDaftarMobilViewModel: DaftarMobilViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mDaftarMobilViewModel = ViewModelProvider(this)[DaftarMobilViewModel::class.java]
        return inflater.inflate(R.layout.fragment_edit_kendaraan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        val argsType = args!!.getInt("arg_type")
        kendaraanId = args!!.getInt("arg_id")

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
        button_save.setOnClickListener {
            mDaftarMobilViewModel.addDaftarMobil(
                Kendaraan(0,edit_namaMobil.text.toString(),
                    edit_Alamat.text.toString(), Integer.parseInt(edit_harga.text.toString()))
            )
            replaceFragment(HomeFragment())
        }
        button_update.setOnClickListener {
            mDaftarMobilViewModel.updateDaftarMobil(
                Kendaraan(kendaraanId,edit_namaMobil.text.toString(),
                    edit_Alamat.text.toString(), Integer.parseInt(edit_harga.text.toString()))
            )
            replaceFragment(HomeFragment())
        }
    }
    fun getKendaraan(){
        mDaftarMobilViewModel.showDaftarMobil(kendaraanId)
        mDaftarMobilViewModel.daftarMobilSelected.observe(viewLifecycleOwner, Observer {
            edit_namaMobil.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.nama)
            edit_Alamat.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.alamat)
            edit_harga.setText(mDaftarMobilViewModel.daftarMobilSelected.value?.harga.toString())
        })
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val args = Bundle()
        fragment.arguments = args
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}