package com.arnava.ur.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.arnava.ur.R
import com.arnava.ur.databinding.FragmentProfileBinding
import com.arnava.ur.ui.activity.AuthActivity
import com.arnava.ur.ui.viewmodel.ProfileViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        viewModel.loadAccountInfo()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.accountFlow.collect { accountInfo ->
                with (binding) {
                    Glide
                        .with(this@ProfileFragment)
                        .load(accountInfo?.snoovatarImg)
                        .apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                        )
                        .into(userIcon)
                    userName.text = accountInfo?.name
                    userTotalFriends.text = "Друзей: ${accountInfo?.numFriends}"
                    userTotalKarma.text = "Карма: ${accountInfo?.totalKarma}"
                }
            }
        }
        binding.friendsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_friendsFragment)
        }
        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}