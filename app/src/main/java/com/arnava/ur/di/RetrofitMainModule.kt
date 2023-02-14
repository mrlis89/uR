package com.arnava.ur.di

import com.arnava.ur.data.api.RedditAuthApi
import com.arnava.ur.data.api.RedditMainApi
import com.arnava.ur.utils.auth.MainModule
import com.arnava.ur.utils.auth.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitMainModule {

    @Provides
    @Singleton
    fun providesAuthorizationInterceptor(): Interceptor = Interceptor { chain ->
        val token = TokenStorage.accessToken
        chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .header("User-Agent", "android:com.arnava.ur:v1.0 (by /u/mr_lis89)")
            .build()
            .let {
                chain.proceed(it)
            }
    }

    @MainModule
    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(interceptor)
            .pingInterval(500, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .build()

    @MainModule
    @Provides
    @Singleton
    fun providesRetrofit(@MainModule okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl("https://oauth.reddit.com/")
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesRedditMainApi(@MainModule retrofit: Retrofit): RedditMainApi =
        retrofit.create(RedditMainApi::class.java)

}

