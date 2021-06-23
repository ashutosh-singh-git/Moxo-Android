package com.rom.moxo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rom.moxo.data.network.response.BlogContentResponse

@Dao
interface BlogContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(blogContent : BlogContentResponse)

    @Query(value = "select * from blog_feed where pId = 0")
    fun getBlogFeed(): LiveData<BlogContentResponse>
}