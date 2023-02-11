package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Post
import com.arnava.ur.databinding.PostLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PagedSubredditListAdapter(
    private val onPhotoClick: (Post) -> Unit,
) : PagingDataAdapter<Post, SubredditListViewHolder>(SubredditDiffUtilCallback()) {
    private val itemExpandedMap = mutableMapOf<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditListViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return SubredditListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubredditListViewHolder, position: Int) {
        val postData = getItem(position)?.data
        itemExpandedMap.putIfAbsent(position, false)
        with(holder.binding) {
            subredditName.text = postData?.title
            Glide
                .with(holder.itemView)
                .load(postData?.url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(bannerView)

            bannerView.isVisible = itemExpandedMap[position] ?: false
            subredditName.setOnClickListener {
                if (postData?.url?.isImage() == true) {
                    bannerView.isVisible = !bannerView.isVisible
                    itemExpandedMap[position] = !itemExpandedMap[position]!!
                }
            }
        }

    }

    fun String.isImage(): Boolean {
        val subStr = this.substring(this.length-3, this.length)
        return (subStr == "jpg" || subStr == "png")
    }
}


class SubredditListViewHolder(val binding: PostLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class SubredditDiffUtilCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) =
        oldItem.data?.id == newItem.data?.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}
