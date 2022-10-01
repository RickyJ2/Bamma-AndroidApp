package com.example.sewakendaraan.entity

import com.example.sewakendaraan.R

class SettingItem(var iconId:Int, var title:String){
    companion object {
        @JvmField
        val listSettingItem = arrayOf(
            SettingItem(0, "Tentang kami"),
            SettingItem(0, "Syarat dan  Ketentuan"),
            SettingItem(0, "Kebijakan dan Privasi"),
            SettingItem(0, "Kritik dan Saran")
        )
    }
}