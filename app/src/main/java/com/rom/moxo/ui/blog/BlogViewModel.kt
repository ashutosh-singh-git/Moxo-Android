package com.rom.moxo.ui.blog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.data.repository.BlogsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BlogViewModel @Inject constructor(
    private val repository: BlogsRepository
) : ViewModel() {
    private var currentResult: Flow<PagingData<Content>>? = null

    @ExperimentalPagingApi
    fun searchBlogs(): Flow<PagingData<Content>> {
        val newResult: Flow<PagingData<Content>> =
            repository.getContent().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

    /**
     * Same thing but with Livedata
     */
    private var currentResultLiveData: LiveData<PagingData<Content>>? = null

    fun searchBlogsLiveData(): LiveData<PagingData<Content>> {
        val newResultLiveData: LiveData<PagingData<Content>> =
            repository.getBlogsLiveData().cachedIn(viewModelScope)
        currentResultLiveData = newResultLiveData
        return newResultLiveData
    }


}