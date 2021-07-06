package com.rom.moxo.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rom.moxo.data.datamodels.Content

@Dao
interface BlogsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contentList: List<Content>)

    @Query(value = "select * from blog_feed")
    fun getContent(): PagingSource<Int, Content>

    @Query("DELETE FROM blog_feed")
    fun clearAll()

    @Query("SELECT COUNT(pId) from blog_feed")
    suspend fun count(): Int
}