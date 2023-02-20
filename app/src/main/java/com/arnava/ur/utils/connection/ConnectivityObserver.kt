package com.arnava.ur.utils.connection

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<Status>
    fun isNetworkConnected(): Boolean

    enum class Status {

        Available, Unavailable, Lost

    }

}