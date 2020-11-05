package com.id6130201483.project.api

import com.id6130201483.project.dataclass.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

    @GET("get-now-orderid-of-customer/{cid}")
    fun getNowOrderID(
        @Path("cid") cid: Int
    ): Call<NowOrderID>

    @DELETE("delete-item-in-order-detail/{oid}/{pid}")
    fun deleteItemInOrderDetail(
        @Path("oid") oid: Int,
        @Path("pid") pid: Int
    ): Call<OrderDetail>

    @GET("select-customer-order-transport/{cid}")
    fun getCustomerOrderTransport(
        @Path("cid") cid: Int
    ): Call<CustomerOrderTransport>

    @PUT("return-product-amount/{pid}/{oamt}")
    fun returnProductAmount(
        @Path("pid") pid: Int,
        @Path("oamt") oamt: Int
    ): Call<ProductAmountUpdate>

    @FormUrlEncoded
    @PUT("change-order-status/{cid}/{oid}")
    fun changeOrderStatus(
        @Path("cid") cid: Int,
        @Path("oid") oid: Int,
        @Field("order_date") order_date: String,
        @Field("status_id") status_id: Int
    ): Call<OrderStatus>

    @FormUrlEncoded
    @POST("create-new-order")
    fun createNewOrder(
        @Field("cus_id") cus_id: Int,
        @Field("order_date") order_date: String
    ): Call<CreateNewOrder>
}