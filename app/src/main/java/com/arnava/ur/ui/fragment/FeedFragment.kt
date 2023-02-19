package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentFeedBinding
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.PostViewModel
import com.arnava.ur.utils.auth.TokenStorage
import com.arnava.ur.utils.constants.THING_DATA
import com.arnava.ur.utils.constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
    private val pagedPostListAdapter = PagedPostListAdapter(
        { onPostClick(it) },
        { onSaveClick(it) },
        { onUnsaveClick(it) },
        { onAuthorClick(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPostListAdapter
        changeBtnState()
        if (viewModel.initialRun) {
            loadInitialList()
            viewModel.initialRun = false
        }
        binding.newPostsBtn.setOnClickListener {
            if (viewModel.currentList != PostViewModel.CurrentList.NEW) {
                viewModel.loadNewPosts()
                viewModel.currentList = PostViewModel.CurrentList.NEW
                changeBtnState()
            }
        }
        binding.topPostsBtn.setOnClickListener {
            if (viewModel.currentList != PostViewModel.CurrentList.TOP) {
                viewModel.loadTopPosts()
                viewModel.currentList = PostViewModel.CurrentList.TOP
                changeBtnState()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagedPosts.collectLatest { pagingDataFlow ->
                pagingDataFlow?.collect {
                    pagedPostListAdapter.submitData(it)
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    viewModel.searchPosts(query)
                    viewModel.currentList = PostViewModel.CurrentList.FOUND
                    changeBtnState()
                }
                return false
            }
        })


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            pagedPostListAdapter.loadStateFlow.collectLatest {
                binding.progressBtn.isVisible = it.refresh is LoadState.Loading
                if (it.refresh is LoadState.Loading) binding.refreshLayout.isVisible = false
                if (it.refresh is LoadState.Error || it.append is LoadState.Error) binding.refreshLayout.isVisible = true
            }
        }
    }

    private fun loadInitialList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                if (TokenStorage.accessToken == null) {
                    binding.progressBtn.isVisible = true
                    delay(500)
                }
                else {
                    binding.progressBtn.isVisible = false
                    viewModel.loadTopPosts()
                    viewModel.putUserNameToStorage()
                    break
                }
            }
        }
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

    private fun onAuthorClick(authorName: String) {
        val bundle = Bundle().apply {
            putString(USER_NAME, authorName)
        }
        findNavController().navigate(R.id.action_navigation_feed_to_userFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, UserFragment::class.java, bundle)
            addToBackStack(FeedFragment::class.java.name)
        }
    }

    private fun onSaveClick(postId: String) = viewModel.savePost(postId)
    private fun onUnsaveClick(postId: String) = viewModel.unsavePost(postId)


    private fun changeBtnState() {
        when (viewModel.currentList) {
            PostViewModel.CurrentList.TOP -> {
                binding.topPostsBtn.isSelected = true
                binding.newPostsBtn.isSelected = false
            }
            PostViewModel.CurrentList.NEW -> {
                binding.newPostsBtn.isSelected = true
                binding.topPostsBtn.isSelected = false
            }
            PostViewModel.CurrentList.FOUND -> {
                binding.topPostsBtn.isSelected = false
                binding.newPostsBtn.isSelected = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}