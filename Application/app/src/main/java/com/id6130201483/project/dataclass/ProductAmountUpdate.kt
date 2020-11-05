package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductAmountUpdate(
    @Expose
    @SerializedName("product_amount") val product_amount: Int
) {
}