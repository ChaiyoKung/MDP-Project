package com.id6130201483.project.dataclass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Employee(
    @Expose
    @SerializedName("emp_id") val emp_id: Int,

    @Expose
    @SerializedName("emp_username") val emp_username: String,

    @Expose
    @SerializedName("emp_password") val emp_password: String,
) {
}