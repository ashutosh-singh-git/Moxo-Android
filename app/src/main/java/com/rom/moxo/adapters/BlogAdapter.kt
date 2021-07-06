package com.rom.moxo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rom.moxo.R
import com.rom.moxo.data.datamodels.Content
import com.rom.moxo.databinding.ItemBinding

class BlogAdapter(private val clicked: (String) -> Unit) :
    PagingDataAdapter<Content, BlogAdapter.ViewHolder>(
        BlogsDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        holder.bind(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class ViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(blogs: Content?){
            binding.apply {
                img.load(blogs?.img) {
                    crossfade(true)
                    placeholder(R.drawable.logo)
                }
                author.text = blogs?.author
                publisher.text = blogs?.publisher
                title.text = blogs?.title
                desc.text = blogs?.description
            }
        }
    }

    private class BlogsDiffCallback : DiffUtil.ItemCallback<Content>() {
        override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem.pId == newItem.pId
        }

        override fun areContentsTheSame(oldItem: Content, newItem: Content): Boolean {
            return oldItem == newItem
        }
    }

}