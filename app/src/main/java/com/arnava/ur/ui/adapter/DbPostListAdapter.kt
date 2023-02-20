package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.R
import com.arnava.ur.data.db.DbPost
import com.arnava.ur.databinding.PostLayoutBinding
import com.bumptech.glide.Glide

class DbPostListAdapter() : RecyclerView.Adapter<DbPostViewHolder>() {

    private var data: List<DbPost?> = emptyList()
    fun setData(data: List<DbPost?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbPostViewHolder {
        val binding = PostLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DbPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DbPostViewHolder, position: Int) {
        val dbPost = data[position]
        with(holder.binding) {
            followBtn.isVisible = false
            saveBtn.isVisible = false
            gotoDetailsBtn.isVisible = false
            authorName.text = dbPost?.postAuthor
            postName.text = dbPost?.postText
            Glide
                .with(holder.itemView)
                .load(dbPost?.imageUrl)
                .placeholder(R.drawable.placeholder_photo)
                .into(bannerView)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class DbPostViewHolder(val binding: PostLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)