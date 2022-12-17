package com.example.sewakendaraan.activity.kritikSaran

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sewakendaraan.R
import com.example.sewakendaraan.data.KritikSaran
import com.example.sewakendaraan.databinding.FragmentKritikSaranBinding
import com.example.sewakendaraan.entity.Constant
import com.example.sewakendaraan.entity.sharedPreferencesKey
import com.example.sewakendaraan.rv.RVItemKritikSaran

class KritikSaranFragment : Fragment() {
    private var binding: FragmentKritikSaranBinding? = null
    private lateinit var rvItemKritikSaran: RecyclerView
    private lateinit var adapter: RVItemKritikSaran
    private var args: Bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentKritikSaranBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as KritikSaranActivity).mKritikSaranViewModel
            kritikSaranFragment = this@KritikSaranFragment
        }

        val layoutManager = LinearLayoutManager(context)
        rvItemKritikSaran = (activity as KritikSaranActivity).findViewById(R.id.rvKritikSaran)
        adapter = RVItemKritikSaran(
            arrayListOf(),
            object: RVItemKritikSaran.OnAdapterListener{
                override fun onUpdate(index: Int) {
                    argEdit(index, Constant.TYPE_UPDATE)
                }
                override fun onDelete(kritikSaran: KritikSaran) {
                    deleteDialog(kritikSaran)
                }
            }
        )

        rvItemKritikSaran.layoutManager = layoutManager
        rvItemKritikSaran.adapter = adapter
        rvItemKritikSaran.addItemDecoration(
            DividerItemDecoration(
                rvItemKritikSaran.context,
                DividerItemDecoration.VERTICAL
            )
        )

        (activity as KritikSaranActivity).mKritikSaranViewModel.kritikSaranList.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }
    private fun deleteDialog(kritikSaran: KritikSaran){
        val alertDialog = AlertDialog.Builder((activity as KritikSaranActivity))
        alertDialog.apply {
            setTitle("Confirmation")
            setMessage("Are you sure to delete this data from ${kritikSaran.content}?")
            setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Delete") { dialogInterface, _ ->
                dialogInterface.dismiss()
                (activity as KritikSaranActivity).deleteKritikSaran(kritikSaran.id)
            }
        }
        alertDialog.show()
    }
    fun argEdit(id: Int, fragmentType: Int){
        args.putInt(sharedPreferencesKey.argsTypeKey, fragmentType)
        args.putInt(sharedPreferencesKey.argsIdKey, id)
        replaceFragment(AddEditKritikSaranFragment())
    }
    private fun  replaceFragment(fragment: Fragment){
        fragment.arguments = args
        val fragmentManager = (activity as KritikSaranActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

}