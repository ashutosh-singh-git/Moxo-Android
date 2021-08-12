package com.rom.moxo.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "bookmarked_feed")
data class Bookmarked(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName( "description")
    val description: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("publishedAt")
    val publishedAt: Long,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("title")
    val title: String
)
