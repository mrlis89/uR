package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.ur.databinding.FragmentFeedBinding
import com.arnava.ur.ui.adapter.PagedSubredditListAdapter
import com.arnava.ur.ui.viewmodel.SubredditsViewModel
import com.arnava.ur.utils.auth.TokenStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SubredditsViewModel by viewModels()
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            while (true) {
                if (TokenStorage.accessToken == null) delay(500)
                else {
                    viewModel.loadTopList()
                    println("DONE")
                    break
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagedSubreddits.collect { pagingDataFlow ->
                pagingDataFlow?.collect {
                    pagedSubredditListAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}