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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UserFragment : Fragment() {

    private var isFriend = false
    private var friendName = ""
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userInfoFlow.collect { userInfo ->
                with(binding) {
                    userInfo?.userInfoData?.let { infoData ->
                        val imageUrl =
                            if (infoData.snoovatarImg != "") infoData.snoovatarImg else infoData.iconImg
                        val image = imageUrl.substringBefore("?")
                        Glide
                            .with(this@UserFragment)
                            .load(image)
                            .apply(
                                RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                            )
                            .into(userIcon)
                        friendName = infoData.name
                        userName.text = friendName
                        userTotalKarma.text = "Карма: ${infoData?.totalKarma}"
                        isFriend = infoData.isFriend
                        checkIsFriend()
                    }
                }
            }
        }
        viewModel.loadUsersPosts(userName)
        viewModel.loadUserInfo(userName)
        with(binding) {
            toFriendBtn.setOnClickListener {
                if (isFriend) {
                    viewModel.deleteFromFriends(friendName)
                    isFriend = false
                    checkIsFriend()
                } else {
                    viewModel.addToFriends(friendName)
                    isFriend = true
                    checkIsFriend()
                }
            }
        }
    }

    private fun FragmentUserBinding.checkIsFriend() {
        toFriendBtn.isSelected = isFriend
        if (isFriend) toFriendBtn.text = "В Друзьях" else toFriendBtn.text = "В друзья"
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

    private fun onSaveClick(data: ThingData) {
        data.fullNameID?.let { viewModel.savePost(it) }
        viewModel.savePostToDb(data)
    }

    private fun onUnsaveClick(data: ThingData) {
        data.fullNameID?.let {
            viewModel.unsavePost(it)
            viewModel.deletePostFromDb(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }
}