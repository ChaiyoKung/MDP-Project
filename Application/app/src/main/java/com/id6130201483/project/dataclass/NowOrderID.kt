package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NowOrderID(
    @Expose
    @SerializedName("order_id") val order_id: Int
) {
}