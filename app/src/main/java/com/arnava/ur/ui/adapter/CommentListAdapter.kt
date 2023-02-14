package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.CommentLayoutBinding

class CommentListAdapter(
    private val onItemClick: (ThingData) -> Unit,
) : RecyclerView.Adapter<CommentListViewHolder>() {

    private var data: List<Thing?> = emptyList()
    fun setData(data: List<Thing?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val binding = CommentLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CommentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val commentData = data[position]?.data
        val nestingLevel = data[position]?.nestingLevel
        with(holder.binding) {
            commentText.text = commentData?.body
            commentAuthor.text = commentData?.author
            rootLayout.setPadding(nestingLevel!! * 30,0,0,0)
        }
    }

    override fun getItemCount() = data.size - 1 
}


class CommentListViewHolder(val binding: CommentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

