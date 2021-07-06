package com.rom.moxo.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.rom.moxo.data.db.BlogDatabase
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.api.ApiService
import com.rom.moxo.data.datasource.BlogsDataSource
import com.rom.moxo.data.remotemediator.BlogsRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BlogsRepository @Inject constructor(
    private val playersApi: ApiService,
    private val db: BlogDatabase
) {


    private val pagingSourceFactory = { db.blogsDao.getContent() }

    /**
     * for caching
     */
    @ExperimentalPagingApi
    fun getContent(): Flow<PagingData<Content>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = BlogsRemoteMediator(
                playersApi,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
    /**
     * Use this if you dont want to cache data to room
     */
//    fun getPlayers(
//    ): Flow<PagingData<Player>> {
//        return Pager(
//            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
//            pagingSourceFactory = {
//                PlayersDataSource(playersApi)
//            }
//        ).flow
//    }

    /**
     * The same thing but with Livedata
     */
    fun getBlogsLiveData(

    ): LiveData<PagingData<Content>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                BlogsDataSource(playersApi)
            }
        ).liveData

    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

}