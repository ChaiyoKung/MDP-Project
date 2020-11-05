package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CustomerOrder(
    @Expose
    @SerializedName("cus_id") val cus_id: Int,

    @Expose
    @SerializedName("cus_fname") val cus_fname: String,

    @Expose
    @SerializedName("cus_lname") val cus_lname: String,

    @Expose
    @SerializedName("cus_address") val cus_address: String,

    @Expose
    @SerializedName("cus_tel") val cus_tel: String,

    @Expose
    @SerializedName("order_id") val order_id: Int,

    @Expose
    @SerializedName("order_date") val order_date: String,

    @Expose
    @SerializedName("order_track") val order_track: String,

    @Expose
    @SerializedName("transport_id") val transport_id: Int,

    @Expose
    @SerializedName("transport_name") val transport_name: String,

    @Expose
    @SerializedName("transport_cost") val transport_cost: Int,

    @Expose
    @SerializedName("status_id") val status_id: Int,

    @Expose
    @SerializedName("status_name") val status_name: String
) {
}