package com.arnava.ur.data.api

import com.arnava.ur.data.model.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RedditAuthApi {

    @POST("access_token")
    suspend fun getAccessToken(
        @Header("Authorization") header: String,
        @Query("grant_type") grantType: String,
        @Query("code") code: String? = null,
        @Query("redirect_uri") redirectUrl: String? = null,
    ): Response<Token>

    @POST("access_token")
    suspend fun refreshToken(
        @Header("Authorization") header: String,
        @Query("grant_type") grantType: String,
        @Query("refresh_token") refreshToken: String
    ): Response<Token>

    @POST("revoke_token")
    suspend fun revokeToken(
        @Header("Authorization") header: String,
        @Query("token") token: String,
        @Query("token_type_hint") tokenTypeHint: String
    ): Response<ResponseBody>
}
