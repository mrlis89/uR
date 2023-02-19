package com.arnava.ur.data.api

import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.users.AccountInfo
import com.arnava.ur.data.model.users.Friends
import com.arnava.ur.data.model.users.UserInfo
import retrofit2.Response
import retrofit2.http.*

interface RedditMainApi {
    //Posts
    @GET("top")
    suspend fun getTopPosts(
        @Query("after") page: String = ""
    ): Listing

    @GET("new")
    suspend fun getNewPosts(
        @Query("after") page: String = ""
    ): Listing

    @GET("search")
    suspend fun searchPosts(
        @Query("after") page: String = "",
        @Query("q") searchRequest: String = "",
    ): Listing

    @GET("user/{username}/saved?type=links")
    suspend fun getSavedPosts(
        @Path("username") username: String,
        @Query("after") page: String = "",
    ): Listing

    @GET("user/{username}/?type=links&sort=top&limit=25")
    suspend fun getUsersTopPosts(
        @Path("username") username: String,
        @Query("after") page: String = "",
    ): String

    //voting
    @POST("/api/vote")
    suspend fun vote(
        @Query("id") id: String,
        @Query("dir") voteDirection: Int,
    )

    //Comments
    @GET("comments/{postId}")
    suspend fun getPostsComments(
        @Path("postId") postId: String,
        @Query("limit") limit: Int = 100
    ): String

    //Users
    @GET("api/v1/me")
    suspend fun getAccountInfo(): AccountInfo

    @GET("user/{username}/about")
    suspend fun getUserInfo(
        @Path("username") userName: String
    ): UserInfo

    //Friends
    @GET("/api/v1/me/friends")
    suspend fun getFriends(): Friends
    @PUT("/api/v1/me/friends/{username}")
    suspend fun addToFriends(
        @Path("username") username: String,
        @Body jsonBody: String
    )

    @DELETE("/api/v1/me/friends/{username}")
    suspend fun deleteFromFriends(
        @Path("username") username: String,
    ): Response<Unit>

    //Saving
    @POST("api/save")
    suspend fun saveThing(
        @Query("category") thingCategory: String,
        @Query("id") thingId: String,
    )

    @POST("api/unsave")
    suspend fun unsaveThing(
        @Query("id") thingId: String,
    )

    @GET("user/{username}/saved?type=comments")
    suspend fun getSavedComments(
        @Path("username") username: String,
        @Query("after") page: String = ""
    ): String

}
