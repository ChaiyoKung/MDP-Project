package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Transport(
    @Expose
    @SerializedName("cus_address") val cus_address: String,

    @Expose
    @SerializedName("transport_id") val transport_id: Int
) {
}