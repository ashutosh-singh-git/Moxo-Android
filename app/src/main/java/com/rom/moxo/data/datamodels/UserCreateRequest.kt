package com.rom.moxo.data.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserCreateRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("msisdn")
    val msisdn: String,
    @SerializedName("passwd")
    val passwd: String,
    @SerializedName("logMode")
    val logMode: String,
    @SerializedName("acqMode")
    val acqMode: String,
    @SerializedName("otp")
    val otp: String
) : Parcelable