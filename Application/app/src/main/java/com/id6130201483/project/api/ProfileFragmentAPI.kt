package com.id6130201483.project.api

import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.dataclass.Order
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ProfileFragmentAPI {
    companion object {
        fun create(): ProfileFragmentAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ProfileFragmentAPI::class.java)
        }
    }

    @GET("select-customer/{cid}")
    fun selectCustomer(
        @Path("cid") cid: Int
    ): Call<Customer>

    @FormUrlEncoded
    @PUT("update-customer-profile/{cid}")
    fun updateCustomer(
        @Path("cid") cid: Int,
        @Field("cus_email") cus_email: String,
        @Field("cus_fname") cus_fname: String,
        @Field("cus_lname") cus_lname: String,
        @Field("cus_address") cus_address: String,
        @Field("cus_tel") cus_tel: String,
        @Field("cus_image") cus_image: String
    ): Call<Customer>

    @FormUrlEncoded
    @PUT("update-order-track/{oid}")
    fun updateOrderTrack(
        @Path("oid") oid: Int,
        @Field("status_id") status_id: Int
    ): Call<Order>
}