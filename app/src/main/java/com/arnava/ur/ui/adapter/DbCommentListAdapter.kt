package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.R
import com.arnava.ur.data.db.DbComment
import com.arnava.ur.databinding.CommentLayoutBinding
import com.arnava.ur.databinding.PostLayoutBinding
import com.bumptech.glide.Glide

class DbCommentListAdapter() : RecyclerView.Adapter<DbCommentViewHolder>() {

    private var data: List<DbComment?> = emptyList()
    fun setData(data: List<DbComment?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DbCommentViewHolder {
        val binding = CommentLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DbCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DbCommentViewHolder, position: Int) {
        val dbComment = data[position]
        with(holder.binding) {
            commentTimeCreated.isVisible = false
            saveCommentBtn.isVisible = false
            commentAuthor.text = dbComment?.commentAuthor
            commentText.text = dbComment?.commentText
            likesCount.text = dbComment?.likesCount.toString()
            rootLayout.setPadding(10, 10, 10, 10)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class DbCommentViewHolder(val binding: CommentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)