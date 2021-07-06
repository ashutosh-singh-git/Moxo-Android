package com.rom.moxo.data.datamodels

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Blogs(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("last")
    val last: String
) : Parcelable