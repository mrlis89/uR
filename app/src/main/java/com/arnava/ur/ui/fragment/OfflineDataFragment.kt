package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.ur.databinding.FragmentSavedToDbBinding
import com.arnava.ur.ui.adapter.DbCommentListAdapter
import com.arnava.ur.ui.adapter.DbPostListAdapter
import com.arnava.ur.ui.viewmodel.DbViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OfflineDataFragment : Fragment() {

    private var _binding: FragmentSavedToDbBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DbViewModel by viewModels()
    private val commentListAdapter = DbCommentListAdapter()
    private val pagedPostListAdapter = DbPostListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedToDbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPostListAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.posts.collect {
                pagedPostListAdapter.setData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.comments.collect {

                commentListAdapter.setData(it)
            }
        }
        binding.savedPostsBtn.setOnClickListener {
            binding.recyclerView.adapter = pagedPostListAdapter
        }
        binding.savedCommentsBtn.setOnClickListener {
            binding.recyclerView.adapter = commentListAdapter
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}