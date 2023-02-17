package com.arnava.ur.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.CommentLayoutBinding
import com.arnava.ur.utils.constants.DATE_FORMAT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class CommentListAdapter(
    private val onThumbUpClick: (String) -> Unit,
    private val onThumbDownClick: (String) -> Unit,
    private val onThumbResetClick: (String) -> Unit,
    private val onSaveComment: (String) -> Unit,
    private val onUnsaveComment: (String) -> Unit,
) : RecyclerView.Adapter<CommentListViewHolder>() {
    private val dateFormat = SimpleDateFormat(DATE_FORMAT)

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
            changeSaveCommentButton(commentData)
            saveCommentBtn.setOnClickListener {
                if (commentData?.saved == true) {
                    commentData.saved = false
                    commentData.fullNameID?.let { onUnsaveComment(it) }
                    changeSaveCommentButton(commentData)
                } else {
                    commentData?.saved = true
                    commentData?.fullNameID?.let { onSaveComment(it) }
                    changeSaveCommentButton(commentData)
                }
            }
            Glide
                .with(this.root)
                .load(commentData?.iconImg)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .circleCrop()
                .into(userIcon)
            commentText.text = commentData?.body
            commentAuthor.text = commentData?.author
            likesCount.text = commentData?.score.toString()
            commentTimeCreated.text = commentData?.createdUtc?.let {
                dateFormat.format(
                    Date(it * 1000)
                )
            }
            setThumbButtons(commentData)
            thumbUpBtn.setOnClickListener {
                if (commentData?.liked == true) {
                    commentData.liked = null
                    commentData.fullNameID?.let { onThumbResetClick(it) }
                }
                else {
                    commentData?.liked = true
                    commentData?.fullNameID?.let { onThumbUpClick(it) }
                }
                setThumbButtons(commentData)
            }
            thumbDownBtn.setOnClickListener {
                if (commentData?.liked == false) {
                    commentData.liked = null
                    commentData.fullNameID?.let { onThumbResetClick(it) }
                }
                else {
                    commentData?.liked = false
                    commentData?.fullNameID?.let { onThumbDownClick(it) }
                }
                setThumbButtons(commentData)
            }
            rootLayout.setPadding(nestingLevel!! * 30, 10, 0, 10)
        }
    }

    private fun CommentLayoutBinding.setThumbButtons(commentData: ThingData?) {
        when (commentData?.liked) {
            true -> {
                thumbUpBtn.isSelected = true
                thumbDownBtn.isSelected = false
            }
            false -> {
                thumbDownBtn.isSelected = true
                thumbUpBtn.isSelected = false
            }
            null -> {
                thumbDownBtn.isSelected = false
                thumbUpBtn.isSelected = false
            }
        }
    }

    private fun CommentLayoutBinding.changeSaveCommentButton(postData: ThingData?) {
        saveCommentBtn.text = if (postData?.saved == true) {
            saveCommentBtn.setTextColor(Color.parseColor("#FF6200EE"))
            "Сохранено"
        } else {
            saveCommentBtn.setTextColor(Color.parseColor("#70000000"))
            "Сохранить"
        }
    }

    override fun getItemCount() = data.size - 1
}


class CommentListViewHolder(val binding: CommentLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

