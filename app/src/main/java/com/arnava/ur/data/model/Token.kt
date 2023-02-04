package com.arnava.ur.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(

    @Json(name = "access_token")
    val accessToken: String,

    @Json(name = "refresh_token")
    val refreshToken: String?,

    @Json(name = "token_type")
    val tokenType: String,

    @Json(name = "expires_in")
    val expiresInSecs: Int,

    @Json(name = "scope")
    val scopes: String
)
