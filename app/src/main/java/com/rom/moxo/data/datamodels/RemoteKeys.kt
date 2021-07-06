package com.rom.moxo.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val pId: Int,
    val prevKey: Int?,
    val nextKey: Int?,
    val isEndReached: Boolean
)
