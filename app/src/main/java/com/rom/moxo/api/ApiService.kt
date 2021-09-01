package com.rom.moxo.api

import com.rom.moxo.data.datamodels.Blogs
import com.rom.moxo.data.datamodels.UserCreateRequest
import com.rom.moxo.data.datamodels.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("feed")
    suspend fun getContent(
        @Query("page") page:Int?,
        @Query("size") size:Int?
    ) : Blogs

    @GET("user/create")
    suspend fun createUser(
        @Body page: UserCreateRequest
    ) : Response<UserInfo>
}