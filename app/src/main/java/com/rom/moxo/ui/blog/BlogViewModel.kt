package com.rom.moxo.ui.blog

import androidx.lifecycle.ViewModel
import com.rom.moxo.data.repository.BlogRepository
import com.rom.moxo.internal.lazydeferred

class BlogViewModel(
    private val blogRepository: BlogRepository
) : ViewModel() {

    val blog by lazydeferred {
         blogRepository.getBlogFeed()
    }
}