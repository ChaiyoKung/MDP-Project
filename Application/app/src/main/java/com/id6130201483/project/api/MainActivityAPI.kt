package com.id6130201483.project.api

import com.id6130201483.project.dataclass.Customer
import com.id6130201483.project.dataclass.Employee
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

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
}