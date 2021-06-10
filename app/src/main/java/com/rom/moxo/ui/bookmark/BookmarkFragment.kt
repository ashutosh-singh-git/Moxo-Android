package com.rom.moxo.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rom.moxo.R
import com.rom.moxo.data.FakeDataSource
import com.rom.moxo.ui.blog.news.DiffUtilNewsItemCallback
import com.rom.moxo.ui.blog.news.NewsListAdapter


class BookmarkFragment : Fragment() {
    private var rvFav: RecyclerView? = null
    private var adapter: NewsListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bookmark_fragment, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initFavList(view)
    }

    private fun initFavList(view: View) {
        var rvFav = this.rvFav;
        var adapter = this.adapter;
        rvFav = view.findViewById(R.id.rv_fav)
        rvFav.setLayoutManager(GridLayoutManager(context, 2))
        rvFav.setHasFixedSize(true)
        adapter = NewsListAdapter(DiffUtilNewsItemCallback())
        rvFav.setAdapter(adapter)
        // get a testing news list from the fake data source
        val fakeDataSource = FakeDataSource()
        adapter.submitList(fakeDataSource.fakeStaticListNews)
    }
}