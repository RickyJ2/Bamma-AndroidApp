package com.example.sewakendaraan.api

import retrofit2.Call
import retrofit2.http.*

interface api {
    @GET("user/{id}")
    fun userData(@Path("id") id:Int? = null):
            Call<ResponseDataUser>
    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("username") username:String?,
        @Field("password") password: String?):
            Call<ResponseDataUser>
    @FormUrlEncoded
    @POST("user/addUser")
    fun addUser(
        @Field("username") username:String?,
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("dateOfBirth") dateofbirth:String?,
        @Field("handphone") handphone:String?,
    ):Call<CreateResponse>
    @FormUrlEncoded
    @PUT("user/{id}")
    fun updateUser(
        @Path("id") id:Int?,
        @Field("username") username:String?,
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("dateOfBirth") dateofbirth:String?,
        @Field("handphone") handphone:String?,
    ):Call<ResponseDataUser>
    //api DaftarMobil
    @GET("daftarMobil/")
    fun showAllDaftarMobil():
            Call<ResponseDaftarMobil>
    @GET("daftarMobil/{id}")
    fun showDaftarMobil(
        @Path("id") id:Int? = null):
            Call<ResponseDaftarMobil>
    @FormUrlEncoded
    @POST("daftarMobil/")
    fun addDaftarMobil(
        @Field("nama") nama:String?,
        @Field("alamat") alamat:String?,
        @Field("harga") harga:Int?,
    ):Call<CreateResponse>
    @FormUrlEncoded
    @PUT("daftarMobil/{id}")
    fun updateDaftarMobil(
        @Path("id") id:Int?,
        @Field("nama") nama:String?,
        @Field("alamat") alamat:String?,
        @Field("harga") harga:Int?,
    ):Call<CreateResponse>
    @DELETE("daftarMobil/{id}")
    fun deleteDaftarMobil(
        @Path("id") id:Int? = null):
            Call<CreateResponse>
}