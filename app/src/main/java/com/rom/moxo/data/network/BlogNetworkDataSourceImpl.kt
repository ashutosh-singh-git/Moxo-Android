package com.rom.moxo.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rom.moxo.data.network.response.BlogContentResponse
import com.rom.moxo.internal.NoConnecticityException

class BlogNetworkDataSourceImpl(
    private val apiInterface: ApiInterface
) : BlogNetworkDataSource {

    private val _downloadedFeed = MutableLiveData<BlogContentResponse>()
    override val downloadedFeed: LiveData<BlogContentResponse>
        get() = _downloadedFeed

    override suspend fun fetchFeed(page: String, size: String) {
        try{
            val fetchedFeed = apiInterface
                .getFeed(page,size)
                .await()
            _downloadedFeed.postValue(fetchedFeed)
        }catch (e: NoConnecticityException){
            Log.e("CONNECTIVITY","No Internet Connection", e)
        }
    }
}