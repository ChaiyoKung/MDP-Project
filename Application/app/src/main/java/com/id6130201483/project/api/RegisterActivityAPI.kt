package com.id6130201483.project.api

import com.id6130201483.project.dataclass.Customer
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterActivityAPI {
    companion object {
        fun create(): RegisterActivityAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(RegisterActivityAPI::class.java)
        }
    }

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("cus_username") cus_username: String,
        @Field("cus_password") cus_password: String,
        @Field("cus_email") cus_email: String,
        @Field("cus_fname") cus_fname: String,
        @Field("cus_lname") cus_lname: String,
        @Field("cus_address") cus_address: String,
        @Field("cus_tel") cus_tel: String,
        @Field("cus_image") cus_image: String
    ): Call<Customer>
}