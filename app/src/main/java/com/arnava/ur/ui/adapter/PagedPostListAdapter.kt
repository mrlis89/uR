package com.arnava.ur.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.App
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.PostLayoutBinding
import com.arnava.ur.utils.common.StringUtils.isImage
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PagedPostListAdapter(
    private val onItemClick: (ThingData) -> Unit,
) : PagingDataAdapter<Thing, PostListViewHolder>(PostDiffUtilCallback()) {
    private val itemExpandedMap = mutableMapOf<Int, Boolean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PostListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        val postData = getItem(position)?.data
        itemExpandedMap.putIfAbsent(position, false)
        with(holder.binding) {
            subredditBtn.text = "r/${ postData?.subreddit }"
            saveBtn.text = if (postData?.saved == true) {
                saveBtn.setTextColor(Color.parseColor("#FF6200EE"))
                "Сохранено"
            } else "Сохранить"
            followBtn.isVisible = false
            postName.text = postData?.title
            Glide
                .with(holder.itemView)
                .load(postData?.url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(bannerView)
            authorName.text = postData?.author
            bannerView.isVisible = itemExpandedMap[position] ?: false
//            authorName.isVisible = itemExpandedMap[position] ?: false
            postName.setOnClickListener {
                if (postData?.url?.isImage() == true) {
                    bannerView.isVisible = !bannerView.isVisible
//                    authorName.isVisible = !authorName.isVisible
                    itemExpandedMap[position] = !itemExpandedMap[position]!!
                }
            }
            gotoDetailsBtn.setOnClickListener {
                postData?.let { onItemClick(it) }
            }
        }

    }

}


class PostListViewHolder(val binding: PostLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class PostDiffUtilCallback : DiffUtil.ItemCallback<Thing>() {
    override fun areItemsTheSame(oldItem: Thing, newItem: Thing) =
        oldItem.data?.id == newItem.data?.id

    override fun areContentsTheSame(oldItem: Thing, newItem: Thing) = oldItem == newItem
}
