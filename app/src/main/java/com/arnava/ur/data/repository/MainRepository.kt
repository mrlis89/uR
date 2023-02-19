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
    //posts
    suspend fun getTopPosts(page: String) = redditMainApi.getTopPosts(page)
    suspend fun getNewPosts(page: String) = redditMainApi.getNewPosts(page)
    suspend fun getSavedPosts(userName: String, page: String) = redditMainApi.getSavedPosts(userName, page)
    suspend fun searchPosts(page: String, request: String) =
        redditMainApi.searchPosts(page, request)
    suspend fun getUsersTopPosts(userName: String, page: String): Listing? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Listing> = moshi.adapter(Listing::class.java)

        val redditResp = redditMainApi.getUsersTopPosts(userName, page)
        //empty child element has wrong type: String instead of null
        val correctedResp = redditResp.replace("\"replies\": \"\"", "\"replies\": null")
        return adapter.fromJson(correctedResp)
    }

    //voting
    suspend fun upVote(id: String) = redditMainApi.vote(id, 1)
    suspend fun downVote(id: String) = redditMainApi.vote(id, -1)
    suspend fun resetVote(id: String) = redditMainApi.vote(id, 0)

    //saving
    suspend fun saveThing(thingCategory: String, thingId: String) =
        redditMainApi.saveThing(thingCategory, thingId)
    suspend fun unsaveThing(thingId: String) = redditMainApi.unsaveThing(thingId)

    //comments
    suspend fun getPostsComments(postId: String): List<Thing> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Listing::class.java)
        val adapter: JsonAdapter<List<Listing>> = moshi.adapter(type)

        val redditResp = redditMainApi.getPostsComments(postId)
        //empty child element has wrong type: String instead of null
        val correctedResp = redditResp.replace("\"replies\": \"\"", "\"replies\": null")
        val resp = adapter.fromJson(correctedResp)
        //0 element is a parent post, 1 element is a comment tree structure
        val commentList = resp?.get(1)?.listData?.things ?: emptyList()

        val list: MutableList<Thing> = mutableListOf()
        commentList.forEach { it.putChildrenTo(list, 0) }

        return list
    }

    suspend fun getSavedComments(userName: String): List<Thing>? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Listing> = moshi.adapter(Listing::class.java)

        val redditResp = redditMainApi.getSavedComments(userName)
        //empty child element has wrong type: String instead of null
        val correctedResp = redditResp.replace("\"replies\": \"\"", "\"replies\": null")
        val resp = adapter.fromJson(correctedResp)
        return resp?.listData?.things
    }

    //users
    suspend fun getAccountInfo() = redditMainApi.getAccountInfo()
    suspend fun getFriendList() = redditMainApi.getFriends()
    suspend fun getUserInfo(userName: String) = redditMainApi.getUserInfo(userName)
    suspend fun addToFriends(userName: String) = redditMainApi.addToFriends(userName, "{\"name\": \"$userName\"}")
    suspend fun deleteFromFriends(userName: String) = redditMainApi.deleteFromFriends(userName)


}