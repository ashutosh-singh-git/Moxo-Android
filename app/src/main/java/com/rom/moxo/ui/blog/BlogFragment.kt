package com.rom.moxo.ui.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.android.volley.Response
import com.rom.moxo.MainActivity
import com.rom.moxo.R


class BlogFragment : Fragment(), OnRefreshListener {
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeResources(R.color.colorAccent)

        topHeadline = view.findViewById(R.id.topheadelines)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.isNestedScrollingEnabled = false

        onLoadingSwipeRefresh("")

        errorLayout = view.findViewById(R.id.errorLayout)
        errorImage = view.findViewById(R.id.errorImage)
        errorTitle = view.findViewById(R.id.errorTitle)
        errorMessage = view.findViewById(R.id.errorMessage)
        btnRetry = view.findViewById(R.id.btnRetry)

    }

    private var recyclerView: RecyclerView? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var articles: List<Article> = ArrayList()
    private var adapter: Adapter? = null
    private var TAG = MainActivity::class.java.simpleName
    private var topHeadline: TextView? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var errorLayout: RelativeLayout? = null
    private var errorImage: ImageView? = null
    private var errorTitle: TextView? = null
    private var errorMessage:TextView? = null
    private var btnRetry: Button? = null

    fun LoadJson(keyword: String) {
        errorLayout!!.visibility = View.GONE
        swipeRefreshLayout!!.isRefreshing = true
        val apiInterface: ApiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
        val country: String = Utils.getCountry()
        val language: String = Utils.getLanguage()
        val call: Call<News>
        call = if (keyword.length > 0) {
            apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY)
        } else {
            apiInterface.getNews(country, API_KEY)
        }
        call.enqueue(object : Callback<News?>() {
            fun onResponse(call: Call<News?>?, response: Response<News?>) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear()
                    }
                    articles = response.body().getArticle()
                    adapter = Adapter(articles, this@BlogFragment)
                    recyclerView!!.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                    initListener()
                    topHeadline!!.visibility = View.VISIBLE
                    swipeRefreshLayout!!.isRefreshing = false
                } else {
                    topHeadline!!.visibility = View.INVISIBLE
                    swipeRefreshLayout!!.isRefreshing = false
                    val errorCode: String
                    errorCode = when (response.code()) {
                        404 -> "404 not found"
                        500 -> "500 server broken"
                        else -> "unknown error"
                    }
                    showErrorMessage(
                        R.drawable.no_result,
                        "No Result",
                        """
                            Please Try Again!
                            $errorCode
                            """.trimIndent()
                    )
                }
            }

            fun onFailure(call: Call<News?>?, t: Throwable) {
                topHeadline!!.visibility = View.INVISIBLE
                swipeRefreshLayout!!.isRefreshing = false
                showErrorMessage(
                    R.drawable.oops,
                    "Oops..",
                    """
                        Network failure, Please Try Again
                        $t
                        """.trimIndent()
                )
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blog, container, false)
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }
}

//class BlogFragment : Fragment() {
//    override fun onViewCreated(
//        view: View,
//        savedInstanceState: Bundle?
//    ) {
//        super.onViewCreated(view, savedInstanceState)
//        var recyclerView: RecyclerView? = null
//        var adapter: NewsListAdapter? = null
//        var swipeRefreshLayout: SwipeRefreshLayout? = null
//
//        recyclerView = view.findViewById(R.id.rv_list_news)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
//        adapter = NewsListAdapter(DiffUtilNewsItemCallback())
//        recyclerView.setAdapter(adapter)
//        // we need a news list data to test the news recyclerview
//        // I have already created a data class that generate a random list of news item
//        val fakeDataSource = FakeDataSource()
//        adapter!!.submitList(fakeDataSource.fakeListNews)
//
//
//        // let's instantiate the swiperefreshlayout we could use view binding, but i will use the classic finviewbyid method
//        swipeRefreshLayout = view.findViewById(R.id.news_list_swipe)
//        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener {
//            // TODO: get the new list of news list will do that when we'll work with a real news api
//            // for now i will just simulate a refresh process
//            val handler = Handler()
//            handler.postDelayed({ // here we update the news list with new data
//                // i will just call a helper method to generate a new updated list
//                adapter!!.submitList(fakeDataSource.fakeUpdatedStaticListNews)
//                swipeRefreshLayout.setRefreshing(false)
//                // as you can see the list news updated successfully but the recyclerview doesn't notify the user about the new data
//                // to do that i will listen for anu data change on the recyclerview and if there is any data added or changed I
//                // will scroll the rv to that data item position....
//                adapter!!.registerAdapterDataObserver(object : AdapterDataObserver() {
//                    override fun onItemRangeInserted(
//                        positionStart: Int,
//                        itemCount: Int
//                    ) {
//                        super.onItemRangeInserted(positionStart, itemCount)
//                        recyclerView.smoothScrollToPosition(positionStart)
//                    }
//                })
//            }, 1200)
//        })
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_blog, container, false)
//    }
//}