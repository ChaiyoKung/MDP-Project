package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderStatus(
    @Expose
    @SerializedName("order_date") val order_date: String,

    @Expose
    @SerializedName("status_id") val status_id: Int
) {
}