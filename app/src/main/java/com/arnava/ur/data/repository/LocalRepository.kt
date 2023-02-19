package com.arnava.ur.data.repository

import android.content.SharedPreferences
import com.arnava.ur.data.model.Token
import com.arnava.ur.utils.auth.TokenStorage
import com.arnava.ur.utils.auth.UserInfoStorage
import javax.inject.Inject

private const val REFRESH_KEY = "refresh_token"
private const val FIRST_RUN = "first_run"
private const val USER_NAME = "user_name"
class LocalRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val editor = sharedPreferences.edit()

    fun getRefreshTokenLocally(): String {
        return sharedPreferences.getString(REFRESH_KEY, "").toString() // empty value by default
    }
    fun getUserNameLocally(): String {
        return sharedPreferences.getString(USER_NAME, "").toString() // empty value by default
    }

    fun saveTokens(token: Token?) {
        TokenStorage.accessToken = token?.accessToken
        TokenStorage.refreshToken = token?.refreshToken
        editor.putString(REFRESH_KEY, token?.refreshToken)
        editor.apply()
    }

    fun clearTokensLocally() {
        editor.remove(REFRESH_KEY)
        editor.apply()
        TokenStorage.accessToken = null
        TokenStorage.refreshToken = null
    }

    fun saveUserName(name: String) {
        UserInfoStorage.userName = name
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    fun isFirstRun(): Boolean {
        if (sharedPreferences.getBoolean(FIRST_RUN, false)) return false
        editor.putBoolean(FIRST_RUN, true)
        editor.apply()
        return true
    }

}