package com.arnava.ur.ui.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.ur.data.model.entity.Post
import com.arnava.ur.data.repository.MainRepository
import javax.inject.Inject

class NewPostPagingSource (
    private val repository: MainRepository,
) :
    PagingSource<String, Post>() {
    override fun getRefreshKey(state: PagingState<String, Post>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getNewPosts(page)
        }.fold(
            onSuccess = {
                val subreddits = it.listData?.posts
                LoadResult.Page(
                    data = subreddits ?: emptyList(),
                    prevKey = null,
                    nextKey = it.listData?.after
                )
            },
            onFailure = {
                LoadResult.Error(it)
            }
        )
    }

    companion object {
        private const val FIRST_PAGE = ""
    }

}