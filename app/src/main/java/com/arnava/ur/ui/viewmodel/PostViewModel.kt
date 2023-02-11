package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.model.entity.Post
import com.arnava.ur.ui.pagingsource.PostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val postPagingSource: PostPagingSource

): ViewModel(){
    private val _pagedSubreddits= MutableStateFlow<Flow<PagingData<Post>>?>(null)
    val pagedSubreddits = _pagedSubreddits.asStateFlow()
    fun loadTopList(){
        _pagedSubreddits.value = Pager(
            config = PagingConfig(pageSize = 25,enablePlaceholders = false),
            pagingSourceFactory = {postPagingSource}
        ).flow.cachedIn(viewModelScope)
    }
}