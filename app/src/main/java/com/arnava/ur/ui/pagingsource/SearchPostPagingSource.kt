package com.arnava.ur.ui.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.repository.MainRepository
import javax.inject.Inject

class SearchPostPagingSource @Inject constructor(
    private val repository: MainRepository,
    private val request :String,
) :
    PagingSource<String, Thing>() {
    override fun getRefreshKey(state: PagingState<String, Thing>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Thing> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.searchPosts(page, request)
        }.fold(
            onSuccess = {
                val subreddits = it.listData?.things
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