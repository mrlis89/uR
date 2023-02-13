package com.arnava.ur.ui.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.repository.MainRepository
import javax.inject.Inject

class TopPostPagingSource @Inject constructor(
    private val repository: MainRepository,
) :
    PagingSource<String, Thing>() {
    override fun getRefreshKey(state: PagingState<String, Thing>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Thing> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getTopPosts(page)
        }.fold(
            onSuccess = {
                val posts = it.listData?.things
                LoadResult.Page(
                    data = posts ?: emptyList(),
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