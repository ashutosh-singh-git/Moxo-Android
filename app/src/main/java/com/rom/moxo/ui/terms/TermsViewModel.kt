package com.rom.moxo.ui.terms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TermsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is terms Fragment"
    }
    val text: LiveData<String> = _text
}