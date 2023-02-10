package com.arnava.ur.ui.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.ur.data.model.entity.Subreddit
import com.arnava.ur.data.repository.MainRepository
import javax.inject.Inject

class SubredditPagingSource @Inject constructor(
    private val repository: MainRepository,
) :
    PagingSource<String, Subreddit>() {
    override fun getRefreshKey(state: PagingState<String, Subreddit>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Subreddit> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getTopSubreddits(page)
        }.fold(
            onSuccess = {
                val subreddits = it.listData?.subreddits
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