package com.id6130201483.project.api

import com.id6130201483.project.dataclass.ProductInCart
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CartActivityAPI {
    companion object {
        fun create(): CartActivityAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(CartActivityAPI::class.java)
        }
    }

    @GET("orderdetail/{cid}")
    fun selectOrderDetail(
        @Path("cid") cid: Int
    ): Call<List<ProductInCart>>
}