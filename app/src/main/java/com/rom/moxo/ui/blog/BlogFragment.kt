package com.rom.moxo.ui.blog

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rom.moxo.R
import com.rom.moxo.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.blog_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class BlogFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: BlogViewModelFactory by instance()

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
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(BlogViewModel::class.java)

//        val apiResponse =  ApiInterface(ConnectivityInterceptorImpl(this.context!!))
//        val blogNetworkDataSource = BlogNetworkDataSourceImpl(apiResponse)
//
//        blogNetworkDataSource.downloadedFeed.observe(this, Observer {
//            textView.text = it.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            blogNetworkDataSource.fetchFeed("0", "20")
//        }
    }
    private fun bindUI() = launch{
        val blogContent = viewModel.blog.await()
        blogContent.observe(this@BlogFragment, Observer {
            if (it == null) return@Observer
            textView.text = it.toString()
        })
    }

}