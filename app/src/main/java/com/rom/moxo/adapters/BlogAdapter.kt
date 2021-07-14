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
import java.text.SimpleDateFormat
import java.util.Date


class BlogAdapter(private val listener: RecyclerViewClickListener) :
    PagingDataAdapter<Content, BlogAdapter.ViewHolder>(
        BlogsDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.itemView , data!!.link)
        }
    }

    inner class ViewHolder(
        private val binding: ItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(blogs: Content?){
            val time = blogs?.publishedAt
            val sdf = SimpleDateFormat("dd/MM/yy")
            val netDate = Date(time!!)
            val date = sdf.format(netDate)

            binding.apply {
                img.load(blogs.img) {
                    crossfade(true)
                    placeholder(R.drawable.placeholder)
                    error(R.drawable.placeholder)
                }
                author.text = blogs.author
                publisher.text = blogs.publisher
                title.text = blogs.title
                desc.text = blogs.description
                publishedAt.text = date
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

