package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.model.entity.Test
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _commentsFlow = MutableStateFlow<List<Thing>?>(emptyList())
    val commentsFlow = _commentsFlow.asStateFlow()

    private val _userInfoFlow = MutableStateFlow<UserInfo?>(null)
    val userInfoFlow = _userInfoFlow.asStateFlow()

    fun loadPostsComments(postId: String) {
        viewModelScope.launch {
            _commentsFlow.value = repository.getPostsComments(postId)
        }

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

}