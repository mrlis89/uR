package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.ur.R
import com.arnava.ur.databinding.FragmentFeedBinding
import com.arnava.ur.ui.adapter.PagedSubredditListAdapter
import com.arnava.ur.ui.viewmodel.PostViewModel
import com.arnava.ur.utils.auth.TokenStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels()
    private val pagedSubredditListAdapter = PagedSubredditListAdapter {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedSubredditListAdapter
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
                    pagedSubredditListAdapter.submitData(it)
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()){
                    viewModel.searchPosts(query)
                }
                return false
            }
        })
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