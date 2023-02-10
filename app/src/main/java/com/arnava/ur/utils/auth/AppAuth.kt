package com.arnava.ur.utils.auth

import android.net.Uri
import androidx.core.net.toUri
import com.arnava.ur.utils.constants.*
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.TokenRequest

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AUTH_URI),
        Uri.parse(TOKEN_URI),
    )

    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = REDIRECT_URL.toUri()

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            CLIENT_ID,
            RESPONSE_TYPE,
            redirectUri
        )
            .setScope(SCOPE)
            .setAdditionalParameters(mapOf("duration" to "permanent"))
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }
}