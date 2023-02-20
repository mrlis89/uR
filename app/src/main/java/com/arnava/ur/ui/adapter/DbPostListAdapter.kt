package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.R
import com.arnava.ur.data.db.DbPost
import com.arnava.ur.databinding.DbPostLayoutBinding
import com.arnava.ur.databinding.PostLayoutBinding
import com.arnava.ur.utils.common.StringUtils.isImage
import com.bumptech.glide.Glide

class DbPostListAdapter() : RecyclerView.Adapter<DbPostViewHolder>() {

    private var data: List<DbPost?> = emptyList()
    fun setData(data: List<DbPost?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbPostViewHolder {
        val binding = DbPostLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DbPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DbPostViewHolder, position: Int) {
        val dbPost = data[position]
        with(holder.binding) {
            postName.setOnClickListener {
                if (dbPost?.imageUrl?.isImage() == true) {
                    bannerView.isVisible = !bannerView.isVisible
                }
            }
            authorName.text = dbPost?.postAuthor
            postName.text = dbPost?.postText
            subredditBtn.text = "r/${dbPost?.subredditName}"
            Glide
                .with(holder.itemView)
                .load(dbPost?.imageUrl)
                .into(bannerView)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class DbPostViewHolder(val binding: DbPostLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)