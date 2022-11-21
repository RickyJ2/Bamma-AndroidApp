package com.example.sewakendaraan.api

import retrofit2.Call
import retrofit2.http.*

interface api {
    @GET("user/{id}")
    fun userData(@Path("id") id:Int? = null):
            Call<ResponseDataUser>
    @GET("user/{username}{password}")
    fun login(
        @Path("username") username:String?,
        @Path("password") password: String?):
            Call<ResponseDataUser>
    @FormUrlEncoded
    @POST("user")
    fun addUser(
        @Field("username") username:String?,
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("dateofbirth") dateofbirth:String?,
        @Field("handphone") handphone:String?,
    ):Call<CreateResponse>
    @FormUrlEncoded
    @PUT("user/{id}")
    fun updateUser(
        @Path("id") id:Int?,
        @Field("username") username:String?,
        @Field("email") email:String?,
        @Field("password") password:String?,
        @Field("dateofbirth") dateofbirth:String?,
        @Field("handphone") handphone:String?,
    ):Call<CreateResponse>
}