package com.arnava.ur.data.api

import com.arnava.ur.data.model.entity.Listing
import com.arnava.ur.data.model.users.Friends
import com.arnava.ur.data.model.users.AccountInfo
import com.arnava.ur.data.model.users.UserInfo
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
    suspend fun accountInfo(): AccountInfo

    @GET("user/{username}/about")
    suspend fun userInfo(
        @Path("username") userName: String): UserInfo

    //Friends
    @GET("/api/v1/me/friends")
    suspend fun getFriends(): Friends

    @PUT("/api/v1/me/friends/{username}")
    suspend fun addToFriends(
        @Path("username") username: String,
        @Body requestBody: String = "\"name\": $username"
    )

    @DELETE("/api/v1/me/friends/{username}")
    suspend fun deleteFromFriends(
        @Path("username") username: String,
    )
}
