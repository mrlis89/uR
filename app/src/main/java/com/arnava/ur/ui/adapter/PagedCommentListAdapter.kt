package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.PostLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PagedCommentListAdapter(
    private val onItemClick: (ThingData) -> Unit,
) : RecyclerView.Adapter<CommentListViewHolder>() {

    private var data: List<Thing?> = emptyList()
    fun setData(data: List<Thing?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CommentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val postData = data[position]?.data
        with(holder.binding) {
            postName.text = postData?.title
            Glide
                .with(holder.itemView)
                .load(postData?.url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(bannerView)
        }
    }

    override fun getItemCount() = data.size
}


class CommentListViewHolder(val binding: PostLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

