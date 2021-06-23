package com.rom.moxo.ui.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rom.moxo.R

class BlogFragment : Fragment() {

    private lateinit var blogViewModel: BlogViewModel

//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<NewsViewHolder>? = null
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        blogViewModel =
            ViewModelProviders.of(this).get(BlogViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_blog, container, false)
        val textView: TextView = root.findViewById(R.id.text_blog)
        recyclerView = root.findViewById(R.id.recycler_view_blog)
        blogViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerView?.adapter = BlogNewsAdapter(generateBlogList());
        recyclerView?.layoutManager = LinearLayoutManager(activity)
    }

    fun generateBlogList(): ArrayList<Blog> {
        val list : ArrayList<Blog> = ArrayList()

        list.add(Blog("Ashu","LWS","https://stackoverflow.com/questions/36896801/how-to-initialize-listt-in-kotlin", "https://image.shutterstock.com/image-vector/default-avatar-profile-icon-social-260nw-1677509740.jpg"))
        list.add(Blog("Toshi","ASP","https://github.com/antoniolg/kotlin-android-example/blob/master/mobile/src/main/kotlin/jp/satorufujiwara/kotlin/ui/main/MainFragment.kt", "http://s3.amazonaws.com/37assets/svn/765-default-avatar.png"))
        list.add(Blog("Vikas","SJA","https://www.vogella.com/tutorials/Retrofit/article.html", "https://image.shutterstock.com/image-vector/default-avatar-profile-icon-social-260nw-1677509740.jpg"))
        list.add(Blog("Asssu","PAL","https://developer.android.com/studio/run", "https://image.shutterstock.com/image-vector/default-avatar-profile-icon-social-260nw-1677509740.jpg"))
        list.add(Blog("Suuany","DJF","https://github.com/topics/fragments?l=kotlin", "https://image.shutterstock.com/image-vector/default-avatar-profile-icon-social-260nw-1677509740.jpg"))
        list.add(Blog("Vllooa","APK","https://github.com/topics/fragments?l=java", "https://image.shutterstock.com/image-vector/default-avatar-profile-icon-social-260nw-1677509740.jpg"))

        return list;
    }

}