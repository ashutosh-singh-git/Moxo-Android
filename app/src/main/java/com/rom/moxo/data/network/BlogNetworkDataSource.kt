package com.rom.moxo.data.network

import androidx.lifecycle.LiveData
import com.rom.moxo.data.network.response.BlogContentResponse

interface BlogNetworkDataSource {
    val downloadedFeed: LiveData<BlogContentResponse>

    suspend fun fetchFeed(
        page: String,
        size: String
    )
}