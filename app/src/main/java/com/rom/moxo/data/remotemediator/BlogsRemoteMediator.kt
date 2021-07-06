package com.rom.moxo.data.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.rom.moxo.api.ApiService
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.data.datamodels.RemoteKeys
import com.rom.moxo.data.db.BlogDatabase
import com.rom.moxo.utils.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class BlogsRemoteMediator(
    private val service: ApiService,
    private val db: BlogDatabase
) : RemoteMediator<Int, Content>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Content>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> {
                if (db.blogsDao.count() > 0) return MediatorResult.Success(false)
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)

            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {

            if (key != null) {
                if (key.isEndReached) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: STARTING_PAGE_INDEX
            val apiResponse = service.getContent( page, state.config.pageSize)

            val blogList = apiResponse.content

            val endOfPaginationReached =
                apiResponse.last == "true"

            db.withTransaction {

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                db.remoteKeysDao.insertKey(
                    RemoteKeys(
                        0,
                        prevKey = prevKey,
                        nextKey = nextKey,
                        isEndReached = endOfPaginationReached
                    )
                )
                db.blogsDao.insert(blogList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RemoteKeys? {
        return db.remoteKeysDao.getKeys().firstOrNull()
    }

}