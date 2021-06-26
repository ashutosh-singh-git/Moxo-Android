package com.rom.moxo.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.rom.moxo.data.db.entity.Content

data class BlogContentResponse(
    @SerializedName("content")
    val content: ArrayList<Content>,
    val number: Int,
    val numberOfElements: Int,
    val size: Int
)