package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreateNewOrder(
    @Expose
    @SerializedName("cus_id") val cus_id: Int,

    @Expose
    @SerializedName("order_date") val order_date: String
) {
}