package com.example.sewakendaraan

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sewakendaraan.databinding.FragmentHomeBinding
import com.example.sewakendaraan.room.kendaraanRoom.Kendaraan
import com.example.sewakendaraan.room.kendaraanRoom.KendaraanDB
import com.example.sewakendaraan.room.Constant
import com.example.sewakendaraan.viewModel.DaftarMobilViewModel
import com.example.sewakendaraan.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    val args = Bundle()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var mDaftarMobilViewModel: DaftarMobilViewModel
    lateinit var kendaraanAdapter: RVKendaraanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mDaftarMobilViewModel = ViewModelProvider(this)[DaftarMobilViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home
        binding.tvWelcome.text = "Welcome, ${context.mUserViewModel.readLoginData?.value?.username.toString()}"

        binding.imageView3.setOnClickListener{
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
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are you sure to delete this data from ${kendaraan.nama}?")
            setNegativeButton("Cancel", DialogInterface.OnClickListener{
                dialogInterface, i ->
                    dialogInterface.dismiss()
            })
            setPositiveButton("Delete", DialogInterface.OnClickListener{
                dialogInterface, i ->
                    dialogInterface.dismiss()
                    mDaftarMobilViewModel.deleteDaftarMobil(kendaraan.id)
                    loadData()
            })
        }
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    fun loadData(){
        mDaftarMobilViewModel.showAllDaftarMobil()
        mDaftarMobilViewModel.daftarMobil.observe(viewLifecycleOwner, Observer {
            mDaftarMobilViewModel.daftarMobil.value?.let { it1 -> kendaraanAdapter.setData(it1) }
        })
    }

    fun argEdit(kendaraanId: Int, fragmentType: Int){
        args.putInt("arg_id", kendaraanId)
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


