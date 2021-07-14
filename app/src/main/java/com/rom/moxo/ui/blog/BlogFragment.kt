package com.rom.moxo.ui.blog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.rom.moxo.activity.DetailActivity
import com.rom.moxo.activity.MainActivity
import com.rom.moxo.adapters.BlogAdapter
import com.rom.moxo.adapters.BlogsLoadingStateAdapter
import com.rom.moxo.adapters.RecyclerViewClickListener
import com.rom.moxo.databinding.BlogFragmentBinding
import com.rom.moxo.utils.RecyclerViewItemDecoration
import com.rom.moxo.utils.isOnline
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class BlogFragment : Fragment(), RecyclerViewClickListener {


    companion object {
        fun newInstance() = BlogFragment()
    }

    private val viewModel: BlogViewModel by activityViewModels()

    private var _binding: BlogFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = BlogAdapter(this)

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BlogFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startSearchJob()

        binding.shimmerLayout.startShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    @ExperimentalPagingApi
    private fun startSearchJob() {

        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            setUpAdapter()
            binding.swiper.setOnRefreshListener {
                adapter.refresh()
            }
            viewModel.searchBlogs()
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun setUpAdapter() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(RecyclerViewItemDecoration())
        }

        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = BlogsLoadingStateAdapter { retry() }
        )

        adapter.addLoadStateListener { loadState ->

            if (loadState.mediator?.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.shimmerLayout.startShimmer()
                    binding.shimmerLayout.isVisible = true
                }

            } else {

                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.isVisible = false
                binding.swiper.isRefreshing = false

                val error = when {
                    loadState.mediator?.prepend is LoadState.Error -> loadState.mediator?.prepend as LoadState.Error
                    loadState.mediator?.append is LoadState.Error -> loadState.mediator?.append as LoadState.Error
                    loadState.mediator?.refresh is LoadState.Error -> loadState.mediator?.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (isOnline) {
                        if (adapter.snapshot().isEmpty()){
                            binding.errorLayout.isVisible = true
                            binding.errorTitle.isVisible = true

                            binding.btnRetry.setOnClickListener {
                                val intent = Intent(context, MainActivity::class.java)
                                context?.startActivity(intent)
                            }
                        }
                    } else {
                        binding.errorLayout.isVisible = true
                        binding.errorMsg.isVisible = true

                        binding.btnRetry.setOnClickListener {
                            val intent = Intent(context, MainActivity::class.java)
                            context?.startActivity(intent)
                        }
                    }
                }

            }
        }

    }

    override fun onRecyclerViewItemClick(view: View, link : String) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("url",link)
        context?.startActivity(intent)
    }

    private fun retry() {
        adapter.retry()
    }
}