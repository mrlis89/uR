package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arnava.ur.data.db.DbPost
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.ThingData
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.data.repository.DbRepository
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
    private val dbRepository: DbRepository,
) : ViewModel() {
    private val _usersPosts = MutableStateFlow<Flow<PagingData<Thing>>?>(null)
    val usersPosts = _usersPosts.asStateFlow()

    private val _userInfoFlow = MutableStateFlow<UserInfo?>(null)
    val userInfoFlow = _userInfoFlow.asStateFlow()

    fun loadUsersPosts(userName: String) {
        _usersPosts.value = Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = { UsersPostPagingSource(repository, userName) }
        ).flow.cachedIn(viewModelScope)
    }

    fun addToFriends(name: String) {
        viewModelScope.launch {
            repository.addToFriends(name)
        }
    }
    fun deleteFromFriends(name: String) {
        viewModelScope.launch {
            repository.deleteFromFriends(name)
        }
    }

    fun loadUserInfo(userName: String) {
        viewModelScope.launch {
            _userInfoFlow.value = repository.getUserInfo(userName)
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
}

