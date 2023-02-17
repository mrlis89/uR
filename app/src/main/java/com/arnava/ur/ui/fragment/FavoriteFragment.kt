package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentFavoriteBinding
import com.arnava.ur.ui.adapter.CommentListAdapter
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.FavoriteViewModel
import com.arnava.ur.utils.constants.THING_DATA
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
        { onUnsavePostClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.savedPostsBtn.setOnClickListener {
            binding.recyclerView.adapter = pagedPostListAdapter
            viewModel.loadSavedPosts("mr_lis89")

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.savedPosts.collectLatest { pagingDataFlow ->
                    pagingDataFlow?.collect {
                        pagedPostListAdapter.submitData(it)
                    }
                }
            }
        }
        binding.savedCommentsBtn.setOnClickListener {
            viewModel.loadSavedComments("mr_lis89")
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

    private fun onPostClick(item: ThingData) {
        val bundle = Bundle().apply {
            putParcelable(THING_DATA, item)
        }
        findNavController().navigate(R.id.action_navigation_feed_to_postDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PostDetailsFragment::class.java, bundle)
            addToBackStack(FeedFragment::class.java.name)
        }
    }

    private fun onSavePostClick(postId: String) = viewModel.savePost(postId)
    private fun onUnsavePostClick(postId: String) = viewModel.unsavePost(postId)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}