package com.arnava.ur.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.databinding.CommentLayoutBinding
import com.arnava.ur.databinding.FriendCardLayoutBinding
import com.arnava.ur.utils.constants.DATE_FORMAT
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

class FriendListAdapter(
    private val onItemClick: (String) -> Unit,
) : RecyclerView.Adapter<FriendListViewHolder>() {
    private var data: List<UserInfo?> = emptyList()
    fun setData(data: List<UserInfo?>) {
        this.data = data
        notifyItemInserted(data.lastIndex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val binding = FriendCardLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return FriendListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        val commentData = data[position]?.userInfoData

        with(holder.binding) {
            Glide
                .with(holder.itemView)
                .load(commentData?.snoovatarImg)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .circleCrop()
                .into(userIcon)
        }
    }



    override fun getItemCount() = data.size
}

class FriendListViewHolder(val binding: FriendCardLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

