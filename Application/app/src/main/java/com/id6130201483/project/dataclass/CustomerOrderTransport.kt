package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CustomerOrderTransport(
    @Expose
    @SerializedName("cus_address") val cus_address: String,

    @Expose
    @SerializedName("transport_id") val transport_id: Int,

    @Expose
    @SerializedName("transport_name") val transport_name: String,

    @Expose
    @SerializedName("transport_cost") val transport_cost: Int
) {
}