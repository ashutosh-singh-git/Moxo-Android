package com.rom.moxo.data.repository

import androidx.lifecycle.LiveData
import com.rom.moxo.data.db.BlogContentDao
import com.rom.moxo.data.db.entity.Content
import com.rom.moxo.data.network.BlogNetworkDataSource
import com.rom.moxo.data.network.response.BlogContentResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class BlogRepositoryImpl(
    private val blogContentDao: BlogContentDao,
    private val blogNetworkDataSource: BlogNetworkDataSource
) : BlogRepository {
    init {
        blogNetworkDataSource.downloadedFeed.observeForever { newBlogFeed ->
            persistFetchedBlogFeed(newBlogFeed)
        }
    }

    override suspend fun getBlogFeed(): LiveData<List<Content>> {
        initBlogData();
        return withContext(Dispatchers.IO){
            return@withContext getBlogFeed()
        }
    }

    private fun persistFetchedBlogFeed(fetchedBlog : BlogContentResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            blogContentDao.upsert(fetchedBlog.content)
        }
    }

    private suspend fun initBlogData(){
        if(isFetchNeeded(ZonedDateTime.now().minusHours(1)))
            fetchBlogFeed()
    }

    private suspend fun fetchBlogFeed(){
        blogNetworkDataSource.fetchFeed("0","20")
    }

    private fun isFetchNeeded(lastFetchTime: ZonedDateTime): Boolean{
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}