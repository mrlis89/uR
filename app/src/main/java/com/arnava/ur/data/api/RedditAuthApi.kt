package com.arnava.ur.data.api

import com.arnava.ur.data.model.Token
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RedditAuthApi {

    @POST()
    suspend fun getAccessToken(
        @Url url: String = "https://www.reddit.com/api/v1/access_token",
        @Header("Authorization") header: String,
        @Query("grant_type") grantType: String,
        @Query("code") code: String? = null,
        @Query("redirect_uri") redirectUrl: String? = null,
    ): Response<Token>

    @POST
    fun renewToken(
        @Url url: String = "https://www.reddit.com/api/v1/access_token",
        @HeaderMap header: HashMap<String, String>,

        @Query("grant_type") grantType: String = "refresh_token",
        @Query("refresh_token") refreshToken: String
    ): Call<Token>

    @POST
    fun revoke(
        @Url url: String = "https://www.reddit.com/api/v1/revoke_token",
        @HeaderMap header: HashMap<String, String>,

        @Query("token") token: String,
        @Query("token_type_hint") tokenTypeHint: String
    ): Call<ResponseBody>
}
