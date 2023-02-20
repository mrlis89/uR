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
import com.arnava.ur.databinding.FragmentSavedToDbBinding
import com.arnava.ur.ui.adapter.CommentListAdapter
import com.arnava.ur.ui.adapter.DbCommentListAdapter
import com.arnava.ur.ui.adapter.DbPostListAdapter
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.DbViewModel
import com.arnava.ur.ui.viewmodel.FavoriteViewModel
import com.arnava.ur.utils.auth.UserInfoStorage
import com.arnava.ur.utils.constants.THING_DATA
import com.arnava.ur.utils.constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}