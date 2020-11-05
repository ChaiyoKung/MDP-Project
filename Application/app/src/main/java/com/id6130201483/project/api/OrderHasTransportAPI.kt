package com.id6130201483.project.api

import com.id6130201483.project.dataclass.Order
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderHasTransportAPI {
    companion object {
        fun create(): OrderHasTransportAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(OrderHasTransportAPI::class.java)
        }
    }

    @GET("select-customer-order-has-transport/{cid}")
    fun selectCustomerOrderHasTransport(
        @Path("cid") cid: Int
    ): Call<List<Order>>

    @GET("select-customer-order-history/{cid}")
    fun selectOrderHistory(
        @Path("cid") cid: Int
    ): Call<List<Order>>
}