package com.arnava.ur.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentFavoriteBinding
import com.arnava.ur.databinding.FragmentPostDetailsBinding
import com.arnava.ur.ui.viewmodel.CommentsViewModel
import com.arnava.ur.utils.constants.THING_DATA
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment() {

    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var postData: ThingData
    private val commentsViewModel: CommentsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let { arg ->
            arg.getParcelable<ThingData>(THING_DATA)?.let {
                postData = it
            }
        }
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.postItem) {
            gotoDetailsBtn.isVisible = false
            postName.text = postData.title
            Glide
                .with(this@PostDetailsFragment)
                .load(postData.url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(bannerView)
            authorName.text = postData.author
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}