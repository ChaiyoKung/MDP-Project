package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Order(
    @Expose
    @SerializedName("order_id") val order_id: Int,

    @Expose
    @SerializedName("order_date") val order_date: String,

    @Expose
    @SerializedName("order_track") val order_track: String,

    @Expose
    @SerializedName("cus_id") val cus_id: Int,

    @Expose
    @SerializedName("transport_id") val transport_id: Int,

    @Expose
    @SerializedName("status_id") val status_id: Int
) {
}