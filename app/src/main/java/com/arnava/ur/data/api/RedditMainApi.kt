package com.arnava.ur.data.api

import com.arnava.ur.data.model.entity.Listing
import retrofit2.http.*

interface RedditMainApi {

    @GET("top")
    suspend fun getTopPosts(
        @Query("after") page: String = ""
    ): Listing

    @GET("new")
    suspend fun getNewPosts(
        @Query("after") page: String = ""
    ): Listing
}
