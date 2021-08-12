package com.rom.moxo.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rom.moxo.data.datamodels.Bookmarked

@Dao
interface BookmarkedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmarked: Bookmarked)

    @Delete
    fun delete(bookmarked: Bookmarked)

    @Query(value = "select * from bookmarked_feed")
    fun getBookmarked(): List<Bookmarked>

    @Query("SELECT * FROM bookmarked_feed WHERE id = :resId")
    fun getBlogById(resId: String) : Bookmarked
}