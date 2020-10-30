package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductInCart(
    @Expose
    @SerializedName("product_id") val product_id: Int,

    @Expose
    @SerializedName("product_image") val product_image: String,

    @Expose
    @SerializedName("product_name") val product_name: String,

    @Expose
    @SerializedName("product_price") val product_price: Int,

    @Expose
    @SerializedName("order_detail_product_amount") val order_detail_product_amount: Int,

    @Expose
    @SerializedName("product_total") val product_total: Int
) {
}