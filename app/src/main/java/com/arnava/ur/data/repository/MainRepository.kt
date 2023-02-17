package com.arnava.ur.data.repository

import com.arnava.ur.data.api.RedditMainApi
import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.entity.Thing
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val redditMainApi: RedditMainApi
) {
    suspend fun getTopPosts(page: String) = redditMainApi.getTopPosts(page)
    suspend fun getNewPosts(page: String) = redditMainApi.getNewPosts(page)
    suspend fun searchPosts(page: String, request: String) =
        redditMainApi.searchPosts(page, request)
    suspend fun upVote(id: String) = redditMainApi.vote(id, 1)
    suspend fun downVote(id: String) = redditMainApi.vote(id, -1)
    suspend fun resetVote(id: String) = redditMainApi.vote(id, 0)
    suspend fun saveThing(thingCategory: String, thingId: String) = redditMainApi.saveThing(thingCategory, thingId)
    suspend fun unsaveThing(thingId: String) = redditMainApi.unsaveThing(thingId)

    suspend fun getPostsComments(postId: String): List<Thing> {
        val redditResp = redditMainApi.getPostsComments(postId)
        //empty child element has wrong type: String instead of null
        val correctedResp = redditResp.replace("\"replies\": \"\"", "\"replies\": null")

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Listing::class.java)
        val adapter: JsonAdapter<List<Listing>> = moshi.adapter(type)

        val resp = adapter.fromJson(correctedResp)
        //0 element is a parent post, 1 element is a comment tree structure
        val commentList = resp?.get(1)?.listData?.things ?: emptyList()

        val list: MutableList<Thing> = mutableListOf()
        commentList.forEach { it.putChildrenTo(list, 0) }

        return list
    }
}