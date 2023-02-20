package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentFavoriteBinding
import com.arnava.ur.ui.adapter.CommentListAdapter
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.FavoriteViewModel
import com.arnava.ur.utils.auth.UserInfoStorage
import com.arnava.ur.utils.constants.THING_DATA
import com.arnava.ur.utils.constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val commentListAdapter = CommentListAdapter(
        { upVote(it) },
        { downVote(it) },
        { resetVote(it) },
        { saveComment(it) },
        { unsaveComment(it) }
    )
    private val pagedPostListAdapter = PagedPostListAdapter(
        { onPostClick(it) },
        { onSavePostClick(it) },
        { onUnsavePostClick(it) },
        { onAuthorClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (viewModel.currentList) {
            FavoriteViewModel.CurrentList.COMMENTS -> {
                loadComments()
            }
            FavoriteViewModel.CurrentList.POSTS -> {
                loadPosts()
            }
        }
        binding.savedPostsBtn.setOnClickListener {
            loadPosts()
        }
        binding.savedCommentsBtn.setOnClickListener {
            loadComments()
            binding.progressBtn.isVisible = true
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.savedPosts.collectLatest { pagingDataFlow ->
                pagingDataFlow?.collect {
                    pagedPostListAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.commentsFlow.collect { comments ->
                comments?.let {
                    commentListAdapter.setData(it)
                    binding.progressBtn.isVisible = false
                }
            }
        }
        binding.refreshBtn.setOnClickListener {
            pagedPostListAdapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            pagedPostListAdapter.loadStateFlow.collectLatest {
                binding.progressBtn.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Loading) binding.refreshLayout.isVisible = false
                if (it.refresh is LoadState.Error || it.append is LoadState.Error) binding.refreshLayout.isVisible = true
            }
        }
    }

    private fun loadPosts() {
        binding.recyclerView.adapter = pagedPostListAdapter
        viewModel.loadSavedPosts(UserInfoStorage.userName.toString())
        viewModel.currentList = FavoriteViewModel.CurrentList.POSTS
        binding.savedPostsBtn.isSelected = true
        binding.savedCommentsBtn.isSelected = false
    }

    private fun loadComments() {
        binding.recyclerView.adapter = commentListAdapter
        viewModel.loadSavedComments(UserInfoStorage.userName.toString())
        viewModel.currentList = FavoriteViewModel.CurrentList.COMMENTS
        binding.savedPostsBtn.isSelected = false
        binding.savedCommentsBtn.isSelected = true
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

    private fun saveComment(comment: ThingData) {
        comment.fullNameID?.let { viewModel.saveComment(it) }
    }

    private fun unsaveComment(comment: ThingData) {
        comment.fullNameID?.let { viewModel.unsaveComment(it) }
    }

    private fun onPostClick(item: ThingData) {
        val bundle = Bundle().apply {
            putParcelable(THING_DATA, item)
        }
        findNavController().navigate(R.id.action_navigation_favorite_to_postDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PostDetailsFragment::class.java, bundle)
            addToBackStack(FavoriteFragment::class.java.name)
        }
    }

    private fun onAuthorClick(authorName: String) {
        val bundle = Bundle().apply {
            putString(USER_NAME, authorName)
        }
        findNavController().navigate(R.id.action_navigation_favorite_to_userFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, UserFragment::class.java, bundle)
            addToBackStack(FavoriteFragment::class.java.name)
        }
    }

    private fun onSavePostClick(data: ThingData) = data.fullNameID?.let { viewModel.savePost(it) }
    private fun onUnsavePostClick(data: ThingData) = data.fullNameID?.let { viewModel.unsavePost(it) }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}