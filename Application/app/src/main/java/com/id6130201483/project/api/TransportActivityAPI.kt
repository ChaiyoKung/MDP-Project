package com.id6130201483.project.api

import com.id6130201483.project.dataclass.CustomerOrderTransport
import com.id6130201483.project.dataclass.Transport
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TransportActivityAPI {
    companion object {
        fun create(): TransportActivityAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(TransportActivityAPI::class.java)
        }
    }

    @GET("select-customer-order-transport/{cid}")
    fun getCustomerOrderTransport(
        @Path("cid") cid: Int
    ): Call<CustomerOrderTransport>

    @FormUrlEncoded
    @PUT("update-address-and-transport/{cid}")
    fun updateAddressAndTransport(
        @Path("cid") cid: Int,
        @Field("cus_address") cus_address: String,
        @Field("transport_id") transport_id: Int
    ): Call<Transport>
}