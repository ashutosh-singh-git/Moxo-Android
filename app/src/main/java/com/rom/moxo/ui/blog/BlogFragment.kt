package com.rom.moxo.ui.blog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rom.moxo.R
import com.rom.moxo.data.network.ApiInterface
import com.rom.moxo.data.network.BlogNetworkDataSourceImpl
import com.rom.moxo.data.network.ConnectivityInterceptorImpl
import kotlinx.android.synthetic.main.blog_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BlogFragment : Fragment() {

    companion object {
        fun newInstance() = BlogFragment()
    }

    private lateinit var viewModel: BlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.blog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)

        val apiResponse =  ApiInterface(ConnectivityInterceptorImpl(this.context!!))
        val blogNetworkDataSource = BlogNetworkDataSourceImpl(apiResponse)

        blogNetworkDataSource.downloadedFeed.observe(this, Observer {
            textView.text = it.toString()
        })

        GlobalScope.launch(Dispatchers.Main) {
            blogNetworkDataSource.fetchFeed("0", "20")
        }
    }

}