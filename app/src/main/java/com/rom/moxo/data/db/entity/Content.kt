package com.rom.moxo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blog_feed")
data class Content(
    val author: String,
    val description: String,
    val id: String,
    val img: String,
    val link: String,
    val publishedAt: Long,
    val publisher: String,
    val title: String
){
    @PrimaryKey(autoGenerate = false)
    var pId : Int = 0
}