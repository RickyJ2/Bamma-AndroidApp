package com.example.sewakendaraan.entity

class SettingItem(var title:String){
    companion object {
        @JvmField
        val listSettingItem = arrayOf(
            SettingItem("Tentang kami"),
            SettingItem("Syarat dan  Ketentuan"),
            SettingItem("Kebijakan dan Privasi"),
            SettingItem("Kritik dan Saran")
        )
    }
}