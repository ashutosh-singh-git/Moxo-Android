package com.rom.moxo.data.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("alias")
    val alias: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("msisdn")
    val msisdn: String
) : Parcelable