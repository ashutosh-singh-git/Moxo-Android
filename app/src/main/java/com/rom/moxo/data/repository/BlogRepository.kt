package com.rom.moxo.data.repository

import androidx.lifecycle.LiveData
import com.rom.moxo.data.db.entity.Content

interface BlogRepository {
    suspend fun getBlogFeed():  LiveData<out List<Content>>
}