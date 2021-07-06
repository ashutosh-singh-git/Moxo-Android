package com.rom.moxo.data.datamodels

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "blog_feed")
data class Content(
    @PrimaryKey(autoGenerate = true)
    var pId: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName( "description")
    val description: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("title")
    val title: String
) : Parcelable