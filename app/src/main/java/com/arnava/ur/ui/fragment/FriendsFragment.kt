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
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.databinding.FragmentFriendsBinding
import com.arnava.ur.ui.adapter.FriendListAdapter
import com.arnava.ur.ui.viewmodel.ProfileViewModel
import com.arnava.ur.utils.constants.USER_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendsFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!
    private val friendListAdapter = FriendListAdapter { onFriendClick(it) }
    private val friendList = mutableListOf<UserInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        viewModel.loadFriendList()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFriendList()
        binding.recyclerView.adapter = friendListAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.friendFlow.collect { friendList ->
                friendList.forEach {
                    viewModel.loadFriendInfo(it.name)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userInfoFlow.collect { userInfo ->
                userInfo?.let {
                    friendList.add(it)
                    friendListAdapter.setData(friendList)
                }
            }
        }
    }

    private fun onFriendClick(friendName: String) {
        val bundle = Bundle().apply {
            putString(USER_NAME, friendName)
        }
        findNavController().navigate(R.id.action_friendsFragment_to_userFragment, bundle)
        parentFragmentManager.commit {
            replace(R.id.nav_host_fragment, UserFragment::class.java, bundle)
            addToBackStack(FriendsFragment::class.java.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}