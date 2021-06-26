package com.rom.moxo.ui.blog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rom.moxo.data.repository.BlogRepository

class BlogViewModelFactory(private val blogRepository: BlogRepository)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BlogViewModel(blogRepository) as T
    }
}