package com.arnava.ur.data.repository

import android.util.Base64
import com.arnava.ur.utils.constants.CLIENT_ID
import com.arnava.ur.utils.constants.REDIRECT_URL
import com.arnava.ur.data.api.RedditAuthApi
import com.arnava.ur.utils.auth.AppAuth
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.EndSessionRequest
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val redditAuthApi: RedditAuthApi
) {

    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    suspend fun getToken(
        authCode: String
    ) {
        val response = redditAuthApi.getAccessToken(
            header = "Basic ${Base64.encodeToString("$CLIENT_ID:".toByteArray(), Base64.NO_WRAP)}",
            grantType = "authorization_code",
            code = authCode,
            redirectUrl = REDIRECT_URL
        )

        if (response.isSuccessful) {
            localRepository.saveTokens(response.body())
        } else {
            throw IOException(response.errorBody().toString())
        }
    }

    suspend fun refreshToken() {
        val response = redditAuthApi.refreshToken(
            header = "Basic ${Base64.encodeToString("$CLIENT_ID:".toByteArray(), Base64.NO_WRAP)}",
            grantType = "refresh_token",
            refreshToken = localRepository.getRefreshTokenLocally()
        )

        if (response.isSuccessful) {
            localRepository.saveTokens(response.body())
        } else {
            throw IOException(response.errorBody().toString())
        }
    }
}