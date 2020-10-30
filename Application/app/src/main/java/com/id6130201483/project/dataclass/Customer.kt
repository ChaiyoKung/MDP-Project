package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Customer(
    @Expose
    @SerializedName("cus_id") val cus_id: Int,

    @Expose
    @SerializedName("cus_username") val cus_username: String,

    @Expose
    @SerializedName("cus_password") val cus_password: String,

    @Expose
    @SerializedName("cus_email") val cus_email: String,

    @Expose
    @SerializedName("cus_fname") val cus_fname: String,

    @Expose
    @SerializedName("cus_lname") val cus_lname: String,

    @Expose
    @SerializedName("cus_address") val cus_address: String,

    @Expose
    @SerializedName("cus_tel") val cus_tel: String,

    @Expose
    @SerializedName("cus_image") val cus_image: String,
) {
}