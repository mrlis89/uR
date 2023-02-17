package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.Test
import com.arnava.ur.data.repository.MainRepository
import com.arnava.ur.ui.pagingsource.SavedPostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _commentsFlow = MutableStateFlow<List<Thing>?>(emptyList())
    val commentsFlow = _commentsFlow.asStateFlow()

    fun loadSavedComments(userName: String) {
        viewModelScope.launch {
            _commentsFlow.value = repository.getSavedComments(userName)
            Test(_commentsFlow.value)
        }
    }

    private val _savedPosts = MutableStateFlow<Flow<PagingData<Thing>>?>(null)
    val savedPosts = _savedPosts.asStateFlow()
    fun loadSavedPosts(userName: String) {
        _savedPosts.value = Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { SavedPostPagingSource(repository, userName) }
        ).flow.cachedIn(viewModelScope)
    }

    fun upVote(commentId: String) {
        viewModelScope.launch {
            repository.upVote(commentId)
        }
    }
    fun downVote(commentId: String) {
        viewModelScope.launch {
            repository.downVote(commentId)
        }
    }
    fun resetVote(commentId: String) {
        viewModelScope.launch {
            repository.resetVote(commentId)
        }
    }

    fun saveComment(commentId: String) {
        viewModelScope.launch {
            repository.saveThing("comments", commentId)
        }
    }
    fun unsaveComment(commentId: String) {
        viewModelScope.launch {
            repository.unsaveThing(commentId)
        }
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