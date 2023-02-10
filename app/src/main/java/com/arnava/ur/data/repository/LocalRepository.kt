package com.arnava.ur.data.repository

import android.content.SharedPreferences
import com.arnava.ur.data.model.Token
import com.arnava.ur.utils.auth.TokenStorage
import javax.inject.Inject

private const val REFRESH_KEY = "refresh_token"
private const val FIRST_RUN = "first_run"
class LocalRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val editor = sharedPreferences.edit()

    fun getRefreshTokenLocally(): String {
        return sharedPreferences.getString(REFRESH_KEY, "").toString() // empty value by default
    }

    fun saveTokens(token: Token?) {
        TokenStorage.accessToken = token?.accessToken
        TokenStorage.refreshToken = token?.refreshToken
        editor.putString(REFRESH_KEY, token?.refreshToken)
        editor.apply()
    }

    fun isFirstRun(): Boolean {
        if (sharedPreferences.getBoolean(FIRST_RUN, false)) return false
        editor.putBoolean(FIRST_RUN, true)
        editor.apply()
        return true
    }

}