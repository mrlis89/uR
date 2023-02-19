package com.arnava.ur.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentPostDetailsBinding
import com.arnava.ur.ui.adapter.CommentListAdapter
import com.arnava.ur.ui.viewmodel.CommentsViewModel
import com.arnava.ur.utils.common.StringUtils.isImage
import com.arnava.ur.utils.constants.THING_DATA
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var postData: ThingData
    private val viewModel: CommentsViewModel by viewModels()
    private var userIsFriend = false
    private val commentListAdapter = CommentListAdapter(
        { upVote(it) },
        { downVote(it) },
        { resetVote(it) },
        { saveComment(it) },
        { unsaveComment(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { arg ->
            arg.getParcelable<ThingData>(THING_DATA)?.let {
                postData = it
            }
        }
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBtn.isVisible = true
        with(binding) {
            postData.author?.let { viewModel.loadUserInfo(it) }
            changeSavePostButton(postData)
            saveBtn.setOnClickListener {
                if (postData.saved == true) {
                    postData.saved = false
                    postData.fullNameID?.let { viewModel.unsavePost(it) }
                    changeSavePostButton(postData)
                } else {
                    postData.saved = true
                    postData.fullNameID?.let { viewModel.savePost(it) }
                    changeSavePostButton(postData)
                }
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.userInfoFlow.collect { userInfo ->
                    userInfo?.userInfoData?.isFriend?.let {
                        userIsFriend = it
                        changeFollowButton(it)
                    }
                }
            }
            followBtn.setOnClickListener {
                if (userIsFriend) {
                    userIsFriend = false
                    postData.author?.let { viewModel.deleteFromFriends(it) }
                    changeFollowButton(userIsFriend)
                } else {
                    userIsFriend = true
                    postData.author?.let { viewModel.addToFriends(it) }
                    changeFollowButton(userIsFriend)
                }
            }

            subredditBtn.text = "r/${postData?.subreddit}"
            gotoDetailsBtn.isVisible = false
            postName.text = postData.title
            val imageSrc = postData.url ?: ""
            if (imageSrc.isImage()) {
                Glide
                    .with(this@PostDetailsFragment)
                    .load(postData.url)
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(bannerView)
            } else bannerView.isVisible = false
            authorName.text = postData.author
            recyclerView.adapter = commentListAdapter
        }
        viewModel.loadPostsComments(postData.id.toString())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.commentsFlow.collect { comments ->
                if (!comments.isNullOrEmpty()) binding.progressBtn.isVisible = false
                comments?.let {commentListAdapter.setData(it)}
            }
        }


    }

    private fun upVote(commentId: String) {
        viewModel.upVote(commentId)
    }

    private fun downVote(commentId: String) {
        viewModel.downVote(commentId)
    }

    private fun resetVote(commentId: String) {
        viewModel.resetVote(commentId)
    }

    private fun saveComment(commentId: String) {
        viewModel.saveComment(commentId)
    }

    private fun unsaveComment(commentId: String) {
        viewModel.unsaveComment(commentId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

    private fun FragmentPostDetailsBinding.changeSavePostButton(postData: ThingData?) {
        saveBtn.text = if (postData?.saved == true) {
            saveBtn.setTextColor(Color.parseColor("#FF6200EE"))
            "Сохранено"
        } else {
            saveBtn.setTextColor(Color.parseColor("#70000000"))
            "Сохранить"
        }
    }

    private fun FragmentPostDetailsBinding.changeFollowButton(userIsFriend: Boolean) {
        followBtn.text = if (userIsFriend) {
            followBtn.setTextColor(Color.parseColor("#FF6200EE"))
            "В друзьях"
        } else {
            followBtn.setTextColor(Color.parseColor("#70000000"))
            "Подписаться"
        }
    }
}