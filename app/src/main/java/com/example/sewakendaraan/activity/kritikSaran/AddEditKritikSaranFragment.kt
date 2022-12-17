package com.example.sewakendaraan.activity.kritikSaran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sewakendaraan.databinding.FragmentAddEditKritikSaranBinding
import com.example.sewakendaraan.entity.Constant
import com.example.sewakendaraan.entity.sharedPreferencesKey

class AddEditKritikSaranFragment : Fragment() {
    private var binding: FragmentAddEditKritikSaranBinding? = null
    private var titleMutableLiveData = MutableLiveData<String>()
    val titleAddEdit: LiveData<String>
        get() = titleMutableLiveData
    private var argsType: Int? = null
    private var argsId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentAddEditKritikSaranBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = (activity as KritikSaranActivity).mKritikSaranViewModel
            addEditKritikSaranFragment = this@AddEditKritikSaranFragment
        }

        getArgs()
    }

    private fun getArgs(){
        val args = arguments
        if(args != null){
            argsType = args.getInt(sharedPreferencesKey.argsTypeKey)
            argsId = args.getInt(sharedPreferencesKey.argsIdKey)
            getTitle()
        }else{
            moveKritikSaran()
        }
    }
    private fun getTitle(){
        if(argsType == Constant.TYPE_CREATE){
            titleMutableLiveData.value = "Add Kritik Saran"
        }else{
            titleMutableLiveData.value = "Edit Kritik Saran"
            setupForm()
        }
    }
    private fun setupForm(){
        (activity as KritikSaranActivity).mKritikSaranViewModel.setupEditForm(argsId!!)
    }
    fun addEdit(){
        if(argsType == Constant.TYPE_CREATE){
            (activity as KritikSaranActivity).addKritikSaran()
        }else{
            (activity as KritikSaranActivity).updateKritikSaran()
        }
    }
    fun moveKritikSaran(){
        (activity as KritikSaranActivity).replaceFragment(KritikSaranFragment())
    }

}