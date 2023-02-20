package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.db.DbComment
import com.arnava.ur.data.db.DbPost
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.data.repository.DbRepository
import com.arnava.ur.data.repository.LocalRepository
import com.arnava.ur.data.repository.MainRepository
import com.arnava.ur.ui.pagingsource.NewPostPagingSource
import com.arnava.ur.ui.pagingsource.SearchPostPagingSource
import com.arnava.ur.ui.pagingsource.TopPostPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: MainRepository,
    private val localRepository: LocalRepository,
    private val dbRepository: DbRepository,
) : ViewModel() {
    var initialRun = true
    var currentList = CurrentList.TOP
    private val _pagedPosts = MutableStateFlow<Flow<PagingData<Thing>>?>(null)
    val pagedPosts = _pagedPosts.asStateFlow()
    fun loadTopPosts() {
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { TopPostPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun loadNewPosts() {
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { NewPostPagingSource(repository) }
        ).flow.cachedIn(viewModelScope)
    }

    fun searchPosts(request: String) {
        _pagedPosts.value = Pager(
            config = PagingConfig(pageSize = 25, enablePlaceholders = false),
            pagingSourceFactory = { SearchPostPagingSource(repository, request) }
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

    fun putUserNameToStorage() {
        var userName = localRepository.getUserNameLocally()
        if (userName != "") localRepository.saveUserName(userName)
        else {
            viewModelScope.launch {
                val accountInfo = repository.getAccountInfo()
                localRepository.saveUserName(accountInfo.name)
            }
        }
    }

    fun savePostToDb(post: ThingData) {
        viewModelScope.launch {
            dbRepository.addPost(
                DbPost(
                    post.fullNameID ?: "",
                    post.subreddit ?: "",
                    post.title ?: "",
                    post.author ?: "",
                    post.url ?: ""
                    )
            )
        }
    }
    fun deletePostFromDb(postId: String) {
        viewModelScope.launch {
            dbRepository.deletePost(postId)
        }
    }

    enum class CurrentList {
        TOP, NEW, FOUND
    }
}

