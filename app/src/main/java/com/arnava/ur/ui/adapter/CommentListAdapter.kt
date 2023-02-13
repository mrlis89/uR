package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.CommentLayoutBinding

class CommentListAdapter(
    private val onItemClick: (ThingData) -> Unit,
) : RecyclerView.Adapter<CommentListViewHolder>() {

    private var data: List<Listing?> = emptyList()
    fun setData(data: List<Listing?>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        val binding = CommentLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return CommentListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val postData = data[1]?.listData?.things!![position].data // 0 element is parent Post
        with(holder.binding) {
            commentText.text = postData?.body
            commentAuthor.text = postData?.author
        }
    }

    override fun getItemCount() = if (data.isEmpty()) 0 else data[1]?.listData?.things?.size!! - 1
}


class CommentListViewHolder(val binding: CommentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

