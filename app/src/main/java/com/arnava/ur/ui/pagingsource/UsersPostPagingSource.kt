package com.arnava.ur.ui.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arnava.ur.data.model.entity.Thing
import com.arnava.ur.data.repository.MainRepository
import javax.inject.Inject

class UsersPostPagingSource @Inject constructor(
    private val repository: MainRepository,
    private val userName: String,
) :
    PagingSource<String, Thing>() {
    override fun getRefreshKey(state: PagingState<String, Thing>): String = FIRST_PAGE

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Thing> {
        val page = params.key ?: FIRST_PAGE
        return kotlin.runCatching {
            repository.getUsersTopPosts(userName, page)
        }.fold(
            onSuccess = {
                val res = mutableListOf<Thing>()
                val posts = it?.listData?.things
                posts?.forEach { post ->
                    if (post.kind == "t3") res.add(post) //reddit response contains wrong info (comments in some situations)
                }
                LoadResult.Page(
                    data = res,
                    prevKey = null,
                    nextKey = it?.listData?.after
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