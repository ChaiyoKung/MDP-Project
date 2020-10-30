package com.id6130201483.project.api

import com.id6130201483.project.dataclass.OrderDetail
import com.id6130201483.project.dataclass.OrderIDForCustomer
import com.id6130201483.project.dataclass.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ProductActivityAPI {
    companion object {
        fun create(): ProductActivityAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ProductActivityAPI::class.java)
        }
    }

    @GET("product/{pid}")
    fun selectProduct(
        @Path("pid") pid: Int
    ): Call<Product>

    @GET("select-orderid-for-customer/{cid}")
    fun selectOrderidForCustomer(
        @Path("cid") cid: Int
    ): Call<OrderIDForCustomer>

//    @FormUrlEncoded
    @PUT("update-product-in-order-detail/{oid}/{pid}/{pamt}")
    fun updateProductInOrderDetail(
        @Path("oid") oid: Int,
        @Path("pid") pid: Int,
        @Path("pamt") pamt: Int
    ): Call<OrderDetail>

    @FormUrlEncoded
    @POST("add-product-to-order-detail")
    fun insertOrderDetail(
        @Field("order_id") order_id: Int,
        @Field("product_id") product_id: Int,
        @Field("order_detail_product_amount") order_detail_product_amount: Int
    ): Call<OrderDetail>
}