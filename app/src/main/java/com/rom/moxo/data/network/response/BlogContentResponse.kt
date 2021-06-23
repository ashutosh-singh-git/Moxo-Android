package com.rom.moxo.data.network.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rom.moxo.data.db.entity.Content

@Entity(tableName = "blog_feed")
data class BlogContentResponse(
    @Embedded(prefix = "content_")
    val content: Content,
    val number: Int,
    val numberOfElements: Int,
    val size: Int
){
    @PrimaryKey(autoGenerate = false)
    var pId : Int = 0
}