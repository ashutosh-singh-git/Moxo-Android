package com.rom.moxo.api

import com.rom.moxo.data.datamodels.Blogs
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("feed")
    suspend fun getContent(
        @Query("page") page:Int?,
        @Query("size") size:Int?
    ) : Blogs
}