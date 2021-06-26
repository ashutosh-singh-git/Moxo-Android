package com.rom.moxo.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rom.moxo.data.db.entity.Content

@Dao
interface BlogContentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(blogContent: List<Content>)

    @Query(value = "select * from blog_feed where pId = 0")
    fun getBlogFeed(): LiveData<List<Content>>
}