package com.rom.moxo.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rom.moxo.api.ApiService
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.utils.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

class BlogsDataSource(private val apiService: ApiService) :
    PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getContent(page,params.loadSize)
            val blogs = response.content
            LoadResult.Page(
                data = blogs,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.last == "true") null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition
    }
}