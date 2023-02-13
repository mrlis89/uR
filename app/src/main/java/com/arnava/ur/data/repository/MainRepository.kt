package com.arnava.ur.data.repository

import com.arnava.ur.data.api.RedditMainApi
import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.entity.Thing
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val redditMainApi: RedditMainApi
) {
    suspend fun getTopPosts(page:String) = redditMainApi.getTopPosts(page)
    suspend fun getNewPosts(page:String) = redditMainApi.getNewPosts(page)
    suspend fun searchPosts(page:String, request :String) = redditMainApi.searchPosts(page, request)
    suspend fun getPostsComments(postId:String): List<Thing>
    = redditMainApi.getPostsComments(postId)[1].listData?.things ?: emptyList()
}