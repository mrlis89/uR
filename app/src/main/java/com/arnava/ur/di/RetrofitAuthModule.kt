package com.arnava.ur.di

import com.arnava.ur.data.api.RedditAuthApi
import com.arnava.ur.utils.auth.AuthModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitAuthModule {

    @AuthModule
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .pingInterval(500, TimeUnit.MILLISECONDS)
            .build()

    @AuthModule
    @Provides
    @Singleton
    fun providesRetrofit(@AuthModule okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://www.reddit.com/api/v1/")
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun providesRedditAuthApi(@AuthModule retrofit: Retrofit): RedditAuthApi =
        retrofit.create(RedditAuthApi::class.java)

}
