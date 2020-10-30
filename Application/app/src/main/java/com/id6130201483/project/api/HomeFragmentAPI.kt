package com.id6130201483.project.api

import com.id6130201483.project.dataclass.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeFragmentAPI {
    companion object {
        fun create(): HomeFragmentAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(HomeFragmentAPI::class.java)
        }
    }

    @GET("select-all-product")
    fun selectAllProduct(): Call<List<Product>>

    @GET("search-product/{pn}")
    fun searchProduct(
        @Path("pn") pn: String
    ): Call<List<Product>>
}