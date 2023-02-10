package com.arnava.ur.data.api

import com.arnava.ur.data.model.entity.Listing
import retrofit2.Response
import retrofit2.http.*

interface RedditMainApi {

    @GET("top")
    suspend fun getTopSubreddits(
        @Query("after") page: String = ""
    ): Listing
}
