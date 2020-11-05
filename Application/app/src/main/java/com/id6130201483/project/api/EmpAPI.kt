package com.id6130201483.project.api

import com.id6130201483.project.dataclass.CustomerOrder
import com.id6130201483.project.dataclass.Order
import com.id6130201483.project.dataclass.OrderDetailProduct
import com.id6130201483.project.dataclass.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface EmpAPI {
    companion object {
        fun create(): EmpAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(EmpAPI::class.java)
        }
    }

    @GET("select-product")
    fun selectAllProduct(): Call<List<Product>>

    @GET("search-product/{pn}")
    fun searchProduct(
        @Path("pn") pn: String
    ): Call<List<Product>>

    @GET("product/{pid}")
    fun selectProduct(
        @Path("pid") pid: Int
    ): Call<Product>

    @FormUrlEncoded
    @PUT("update-product/{pid}")
    fun updateProduct(
        @Path("pid") pid: Int,
        @Field("product_name") product_name: String,
        @Field("product_detail") product_detail: String,
        @Field("product_amount") product_amount: Int,
        @Field("product_price") product_price: Int,
        @Field("product_image") product_image: String
    ): Call<Product>

    @FormUrlEncoded
    @POST("insert-product")
    fun insertProduct(
        @Field("product_name") product_name: String,
        @Field("product_detail") product_detail: String,
        @Field("product_amount") product_amount: Int,
        @Field("product_price") product_price: Int,
        @Field("product_image") product_image: String
    ): Call<Product>

    @DELETE("delete-product/{pid}")
    fun deleteProduct(
        @Path("pid") pid: Int
    ): Call<Product>

    @GET("select-customer-order")
    fun selectAllCusOrder(): Call<List<Order>>

    @GET("select-order-detail/{oid}")
    fun selectCustomerOrder(
        @Path("oid") oid: Int
    ): Call<CustomerOrder>

    @GET("select-order-detail-product/{oid}")
    fun selectOrderDetailProduct(
        @Path("oid") oid: Int
    ): Call<List<OrderDetailProduct>>

    @FormUrlEncoded
    @PUT("update-order-track/{oid}")
    fun updateOrderTrack(
        @Path("oid") oid: Int,
        @Field("order_track") order_track: String,
        @Field("status_id") status_id: Int
    ): Call<Order>
}