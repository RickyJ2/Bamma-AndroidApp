package com.example.sewakendaraan

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sewakendaraan.activity.home.HomeActivity
import com.example.sewakendaraan.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    lateinit var kendaraanAdapter: RVKendaraanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        //setupRecyclerView()
    }
//    private fun setupRecyclerView(){
//        val context = context as HomeActivity
//        kendaraanAdapter = RVKendaraanAdapter(arrayListOf(), object : RVKendaraanAdapter.OnAdapterListener{
//            override fun onClick(daftarMobil: DaftarMobil){
//                argEdit(daftarMobil.id, Constant.TYPE_READ)
//            }
//            override fun onUpdate(daftarMobil: DaftarMobil){
//                argEdit(daftarMobil.id, Constant.TYPE_UPDATE)
//            }
//            override fun onDelete(daftarMobil: DaftarMobil) {
//                deleteDialog(daftarMobil)
//            }
//        })
//        rvKendaraan.apply{
//            layoutManager = LinearLayoutManager(context)
//            adapter = kendaraanAdapter
//        }
//    }
//
//    private fun deleteDialog(daftarMobil: DaftarMobil){
//        val context = context as HomeActivity
//        val db by lazy { KendaraanDB(context) }
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.apply {
//            setTitle("Confirmation")
//            setMessage("Are you sure to delete this data from ${daftarMobil.jenisKendaraan}?")
//            setNegativeButton("Cancel", DialogInterface.OnClickListener{
//                dialogInterface, i ->
//                    dialogInterface.dismiss()
//            })
//            setPositiveButton("Delete", DialogInterface.OnClickListener{
//                dialogInterface, i ->
//                    dialogInterface.dismiss()
//                    CoroutineScope(Dispatchers.IO).launch {
//                        db.kendaraanDao().deleteKendaraan(daftarMobil)
//                        loadData()
//                    }
//            })
//        }
//        alertDialog.show()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        loadData()
//    }
//
//    fun loadData(){
//        val context = context as HomeActivity
//        val db by lazy { KendaraanDB(context) }
//        CoroutineScope(Dispatchers.IO).launch {
//            val kendaraan = db.kendaraanDao().getKendaraan()
//            withContext(Dispatchers.Main){
//                kendaraanAdapter.setData(kendaraan)
//            }
//        }
//    }
//
//    fun argEdit(kendaraanId: Int, fragmentType: Int){
//        args.putInt("arg_type", fragmentType)
//        replaceFragment(EditKendaraanFragment())
//    }
//    private fun  replaceFragment(fragment: Fragment){
//        val context = context as HomeActivity
//        fragment.arguments = args
//        val fragmentManager = context.supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frameLayout,fragment)
//        fragmentTransaction.commit()
//    }
}


