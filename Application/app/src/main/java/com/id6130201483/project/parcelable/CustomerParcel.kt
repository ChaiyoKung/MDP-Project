package com.id6130201483.project.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerParcel(
    val id: Int,
    val username: String,
    val email: String,
    val fname: String,
    val lname: String,
    val address: String,
    val tel: String,
    val imageURL: String
) : Parcelable {
}