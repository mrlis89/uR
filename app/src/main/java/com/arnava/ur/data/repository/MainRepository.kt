package com.arnava.ur.data.repository

import android.util.Base64
import com.arnava.ur.utils.constants.CLIENT_ID
import com.arnava.ur.utils.constants.REDIRECT_URL
import com.arnava.ur.data.api.RedditAuthApi
import com.arnava.ur.data.api.RedditMainApi
import com.arnava.ur.utils.auth.AppAuth
import net.openid.appauth.AuthorizationRequest
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val redditMainApi: RedditMainApi
) {
    suspend fun getTopSubreddits(page:String) = redditMainApi.getTopSubreddits(page)
}