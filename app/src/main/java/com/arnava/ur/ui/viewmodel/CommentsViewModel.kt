package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _pagingCollections = MutableStateFlow<List<Thing>?>(emptyList())
    val pagingCollections = _pagingCollections.asStateFlow()

    fun loadFoundCollections(postId: String) {
        viewModelScope.launch {
            _pagingCollections.value = repository.getPostsComments(postId).listData?.things
        }

    }
}