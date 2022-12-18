package com.example.sewakendaraan.api

import com.example.sewakendaraan.api.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface API {
    //user
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
    @POST("user/register")
    fun register(
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
    @FormUrlEncoded
    @POST("user/reqResetPassword")
    fun reqResetPassword(
        @Field("username") username:String?
    ):Call<CreateResponse>
    @Multipart
    @POST("user/updateProfile/{id}")
    fun updateProfile(
        @Part image: MultipartBody.Part
    ):Call<ResponseDataUser>

    //DaftarMobil
    @GET("daftarMobil/show")
    fun getDaftarMobil():
            Call<ResponseDataDaftarMobil>
    @GET("daftarMobil/{id}")
    fun getDaftarMobilAt(@Path("id") id:Int? = null):
            Call<ResponseDataMobil>

    //Cabang
    @GET("cabang/show")
    fun getCabang():
            Call<ResponseDataCabang>
    @GET("cabang/{id}")
    fun getCabangAt(@Path("id") id:Int? = null):
            Call<ResponseDataCabang>

    //Pemesanan
    @GET("pemesanan/{id_user}")
    fun getPemesanan(@Path("id_user") id_user:Int? = null):
            Call<ResponseDataPemesanan>
    @FormUrlEncoded
    @POST("pemesanan/add")
    fun addPemesanan(
        @Field("id_mobil") id_mobil:Int?,
        @Field("id_user") id_user:Int?,
        @Field("durasi") durasi:String?,
        @Field("tgl_peminjaman") tgl_peminjaman:String?,
    ):Call<ResponseDataPemesanan>
    @FormUrlEncoded
    @PUT("pemesanan/{id}")
    fun updatePemesanan(
        @Path("id") id:Int?,
        @Field("id_mobil") id_mobil:Int?,
        @Field("id_user") id_user:Int?,
        @Field("durasi") durasi:String?,
        @Field("tgl_peminjaman") tgl_peminjaman:String?,
    ):Call<ResponseDataPemesanan>

    //KritikSaran
    @GET("kritikSaran/{id_user}")
    fun getKritikSaran(@Path("id_user") id_user:Int? = null):
            Call<ResponseDataKritikSaran>
    @FormUrlEncoded
    @POST("kritikSaran/add")
    fun addKritikSaran(
        @Field("id_user") id_user:Int?,
        @Field("content") content:String?,
    ):Call<ResponseDataKritikSaran>
    @FormUrlEncoded
    @PUT("kritikSaran/{id}")
    fun updateKritikSaran(
        @Path("id") id:Int?,
        @Field("id_user") id_user:Int?,
        @Field("content") content:String?,
    ):Call<ResponseDataKritikSaran>
    @DELETE("kritikSaran/{id}")
    fun deleteKritiKSaran(@Path("id") id:Int?= null):
            Call<ResponseDataKritikSaran>

    //Rating
    @FormUrlEncoded
    @POST("rating/get/{id_user}")
    fun getRating(
        @Path("id_user") id_user:Int? = null,
        @Field("id_mobil") id_mobil:Int? = null):
            Call<ResponseDataRating>
    @FormUrlEncoded
    @POST("rating/addupdate")
    fun addupdateRating(
        @Field("id_user") id_user:Int?,
        @Field("id_mobil") id_mobil:Int?,
        @Field("rating") rating:Int?,
    ):Call<ResponseDataRating>
    @DELETE("rating/{id}")
    fun deleteRating(@Path("id") id:Int?= null):
            Call<ResponseDataRating>
}