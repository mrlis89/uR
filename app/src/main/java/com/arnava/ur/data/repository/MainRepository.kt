package com.arnava.ur.data.repository

import com.arnava.ur.data.api.RedditMainApi
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val redditMainApi: RedditMainApi
) {
    suspend fun getTopSubreddits(page:String) = redditMainApi.getTopPosts(page)
}