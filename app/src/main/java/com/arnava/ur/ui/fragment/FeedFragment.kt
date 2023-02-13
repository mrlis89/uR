package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.ur.R
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentFeedBinding
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.PostViewModel
import com.arnava.ur.utils.auth.TokenStorage
import com.arnava.ur.utils.constants.THING_DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
    private val pagedPostListAdapter = PagedPostListAdapter { onPostClick(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPostListAdapter
        binding.topPostsBtn.isSelected = true
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                if (TokenStorage.accessToken == null) delay(500)
                else {
                    viewModel.loadTopPosts()
                    break
                }
            }
        }
        binding.newPostsBtn.setOnClickListener {
            viewModel.loadNewPosts()
            if (!binding.newPostsBtn.isSelected) changeBtnState()
        }
        binding.topPostsBtn.setOnClickListener {
            viewModel.loadTopPosts()
            if (!binding.topPostsBtn.isSelected) changeBtnState()
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
                }
                return false
            }
        })
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

    private fun changeBtnState() {
        binding.topPostsBtn.isSelected = !binding.topPostsBtn.isSelected
        binding.newPostsBtn.isSelected = !binding.newPostsBtn.isSelected
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}