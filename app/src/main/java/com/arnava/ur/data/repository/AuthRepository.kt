package com.arnava.ur.data.repository

import android.util.Base64
import com.arnava.ur.data.api.RedditAuthApi
import com.arnava.ur.utils.auth.AppAuth
import com.arnava.ur.utils.auth.TokenStorage
import com.arnava.ur.utils.constants.CLIENT_ID
import com.arnava.ur.utils.constants.REDIRECT_URL
import net.openid.appauth.AuthorizationRequest
import java.io.IOException
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val localRepository: LocalRepository,
    private val redditAuthApi: RedditAuthApi
) {
    private val header =
        "Basic ${Base64.encodeToString("$CLIENT_ID:".toByteArray(), Base64.NO_WRAP)}"

    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    suspend fun getAccessToken(
        authCode: String
    ) {
        val response = redditAuthApi.getAccessToken(
            header = header,
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
            header = header,
            grantType = "refresh_token",
            refreshToken = localRepository.getRefreshTokenLocally()
        )

        if (response.isSuccessful) {
            localRepository.saveTokens(response.body())
        } else {
            throw IOException(response.errorBody().toString())
        }
    }

    suspend fun revokeRefreshToken() {
        redditAuthApi.revokeToken(
            header = header,
            token = TokenStorage.refreshToken.toString(),
            tokenTypeHint = "refresh_token"
        )
    }
    suspend fun revokeAccessToken() {
        redditAuthApi.revokeToken(
            header = header,
            token = TokenStorage.accessToken.toString(),
            tokenTypeHint = "access_token"
        )
    }
}