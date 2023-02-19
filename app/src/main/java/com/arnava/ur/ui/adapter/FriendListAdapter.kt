package com.arnava.ur.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arnava.ur.R
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.databinding.FriendCardLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

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
        val userInfoData = data[position]?.userInfoData

        with(holder.binding) {
            userName.text = userInfoData?.name
            val imageUrl = if (userInfoData?.snoovatarImg != "") userInfoData?.snoovatarImg else userInfoData?.iconImg
            val image = imageUrl?.substringBefore("?")
            Glide
                .with(holder.itemView)
                .load(image)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(userIcon)
            root.setOnClickListener {
                userInfoData?.name?.let { onItemClick(it) }
            }
        }
    }



    override fun getItemCount() = data.size
}

class FriendListViewHolder(val binding: FriendCardLayoutBinding) :
    RecyclerView.ViewHolder(binding.root)

