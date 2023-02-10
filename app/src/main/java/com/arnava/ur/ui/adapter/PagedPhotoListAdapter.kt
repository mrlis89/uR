package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.Subreddit
import com.arnava.ur.databinding.SubredditLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PagedSubredditListAdapter(
    private val onPhotoClick: (Subreddit) -> Unit,
) : PagingDataAdapter<Subreddit, SubredditListViewHolder>(SubredditDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditListViewHolder {
        val binding = SubredditLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return SubredditListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubredditListViewHolder, position: Int) {
        val subreddit = getItem(position)?.data
        with(holder.binding) {
            subredditName.text = subreddit?.name
            subredditDescription.text = subreddit?.description
            Glide
                .with(holder.itemView)
                .load(subreddit?.headerImg)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .placeholder(R.drawable.placeholder_photo)
                .into(iconView)

            root.setOnClickListener {

            }
        }

    }
}


class SubredditListViewHolder(val binding: SubredditLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

class SubredditDiffUtilCallback : DiffUtil.ItemCallback<Subreddit>() {
    override fun areItemsTheSame(oldItem: Subreddit, newItem: Subreddit) =
        oldItem.data?.id == newItem.data?.id

    override fun areContentsTheSame(oldItem: Subreddit, newItem: Subreddit) = oldItem == newItem
}
