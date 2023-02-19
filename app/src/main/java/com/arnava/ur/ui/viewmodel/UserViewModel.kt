package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.repository.MainRepository
import com.arnava.ur.ui.pagingsource.SavedPostPagingSource
import com.arnava.ur.ui.pagingsource.TopPostPagingSource
import com.arnava.ur.ui.pagingsource.UsersPostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _usersPosts = MutableStateFlow<Flow<PagingData<Thing>>?>(null)
    val usersPosts = _usersPosts.asStateFlow()
    fun loadUsersPosts(userName: String) {
        _usersPosts.value = Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { UsersPostPagingSource(repository, userName) }
        ).flow.cachedIn(viewModelScope)
    }

    fun savePost(postId: String) {
        viewModelScope.launch {
            repository.saveThing("posts", postId)
        }
    }

    fun unsavePost(postId: String) {
        viewModelScope.launch {
            repository.unsaveThing(postId)
        }
    }
}

