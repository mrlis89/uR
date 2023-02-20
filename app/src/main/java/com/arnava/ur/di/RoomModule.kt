package com.arnava.ur.di

import android.content.Context
import androidx.room.Room
import com.arnava.ur.data.db.AppDB
import com.arnava.ur.data.db.RedditDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule{
    @Singleton
    @Provides
    fun providesPhotoDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AppDB::class.java, "redditDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesPhotoDAO(appDB: AppDB): RedditDAO {
        return appDB.getPhotoDao()
    }
}