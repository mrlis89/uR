package com.arnava.ur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.databinding.FragmentPostDetailsBinding
import com.arnava.ur.ui.adapter.CommentListAdapter
import com.arnava.ur.ui.viewmodel.CommentsViewModel
import com.arnava.ur.utils.common.StringUtils.isImage
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
    private val viewModel: CommentsViewModel by viewModels()
    private val commentListAdapter = CommentListAdapter {}

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
            val imageSrc = postData.url ?: ""
            if (imageSrc.isImage()) {
                Glide
                    .with(this@PostDetailsFragment)
                    .load(postData.url)
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .into(bannerView)
            } else bannerView.isVisible = false
            authorName.text = postData.author
        }
        binding.recyclerView.adapter = commentListAdapter
        viewModel.loadFoundCollections(postData.id.toString())
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.commentsFlow.collect { comments ->
                comments?.let { commentListAdapter.setData(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}