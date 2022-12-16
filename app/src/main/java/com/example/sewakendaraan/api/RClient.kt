package com.example.sewakendaraan.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RClient {
    private const val BASE_URL = "http://192.168.0.103/sewaKendaraanBamma/"

    val instances:API by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL + "public/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(API::class.java)
    }
    fun imageBaseUrl(): String{
        return this.BASE_URL + "storage/app/public/"
    }
}