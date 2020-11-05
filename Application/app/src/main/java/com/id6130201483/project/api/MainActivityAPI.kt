package com.id6130201483.project.api

import com.id6130201483.project.dataclass.CreateNewOrder
import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.dataclass.Employee
import com.id6130201483.project.dataclass.Order
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MainActivityAPI {
    companion object {
        fun create(): MainActivityAPI {
            return Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(MainActivityAPI::class.java)
        }
    }

    @GET("loginCus/{u}/{p}")
    fun loginCus(
        @Path("u") u: String,
        @Path("p") p: String
    ): Call<Customer>

    @GET("loginEmp/{u}/{p}")
    fun loginEmp(
        @Path("u") u: String,
        @Path("p") p: String
    ): Call<Employee>

    @FormUrlEncoded
    @POST("create-new-order")
    fun createNewOrder(
        @Field("cus_id") cus_id: Int,
        @Field("order_date") order_date: String
    ): Call<CreateNewOrder>

    @GET("check-customer-order/{cid}")
    fun checkCustomerOrder(
        @Path("cid") cid: Int
    ): Call<Order>
}