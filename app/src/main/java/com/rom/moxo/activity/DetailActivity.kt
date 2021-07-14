package com.rom.moxo.activity

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.webkit.WebView
import androidx.core.view.isVisible
import com.rom.moxo.databinding.BlogDetailBinding
import com.rom.moxo.utils.isOnline


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: BlogDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = BlogDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val url = intent.getStringExtra("url")

        if(isOnline){
            if (url != null){
                binding.blogDetail.settings.apply {
                    javaScriptEnabled = true
                }
                binding.blogDetail.apply {
                    webViewClient = object : WebViewClient(){

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            super.onPageStarted(view, url, favicon)
                            binding.progressBar.isVisible = false
                            binding.blogDetail.isVisible = true
                        }
                    }
                }
                binding.blogDetail.loadUrl(url)
            }
        } else {
            binding.errorLayout.isVisible = true
        }

    }
}