package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderDetail(
    @Expose
    @SerializedName("order_id") val order_id: Int,

    @Expose
    @SerializedName("product_id") val product_id: Int,

    @Expose
    @SerializedName("order_detail_product_amount") val order_detail_product_amount: Int,
) {
}