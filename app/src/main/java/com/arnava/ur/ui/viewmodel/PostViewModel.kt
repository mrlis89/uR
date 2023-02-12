package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.model.entity.Post
import com.arnava.ur.data.repository.MainRepository
import com.arnava.ur.ui.pagingsource.NewPostPagingSource
import com.arnava.ur.ui.pagingsource.SearchPostPagingSource
import com.arnava.ur.ui.pagingsource.TopPostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel@Inject constructor(
    private val repository: MainRepository,
): ViewModel(){
    private val _pagedPosts= MutableStateFlow<Flow<PagingData<Post>>?>(null)
    val pagedPosts = _pagedPosts.asStateFlow()
    fun loadTopPosts(){
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25,enablePlaceholders = false),
            pagingSourceFactory = {TopPostPagingSource(repository)}
        ).flow.cachedIn(viewModelScope)
    }

    fun loadNewPosts(){
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25,enablePlaceholders = false),
            pagingSourceFactory = {NewPostPagingSource(repository)}
        ).flow.cachedIn(viewModelScope)
    }

    fun searchPosts(request :String){
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25,enablePlaceholders = false),
            pagingSourceFactory = {SearchPostPagingSource(repository, request)}
        ).flow.cachedIn(viewModelScope)
    }
}