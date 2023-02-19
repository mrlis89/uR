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
import com.arnava.ur.databinding.FragmentUserBinding
import com.arnava.ur.ui.adapter.PagedPostListAdapter
import com.arnava.ur.ui.viewmodel.UserViewModel
import com.arnava.ur.utils.constants.THING_DATA
import com.arnava.ur.utils.constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var userName: String
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels()
    private val pagedPostListAdapter = PagedPostListAdapter(
        { onPostClick(it) },
        { onSaveClick(it) },
        { onUnsaveClick(it) },
        {}
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { arg ->
            arg.getString(USER_NAME)?.let {
                userName = it
            }
        }
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = pagedPostListAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.usersPosts.collectLatest { pagingDataFlow ->
                pagingDataFlow?.collect {
                    pagedPostListAdapter.submitData(it)
                }
            }
        }
        viewModel.loadUsersPosts(userName)
    }

    private fun onPostClick(item: ThingData) {
        val bundle = Bundle().apply {
            putParcelable(THING_DATA, item)
        }
        findNavController().navigate(R.id.action_userFragment_to_postDetailsFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, PostDetailsFragment::class.java, bundle)
            addToBackStack(FeedFragment::class.java.name)
        }
    }

    private fun onSaveClick(postId: String) = viewModel.savePost(postId)
    private fun onUnsaveClick(postId: String) = viewModel.unsavePost(postId)

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}