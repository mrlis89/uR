package com.arnava.ur.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbPost::class, DbComment::class], version = 1)
abstract class AppDB: RoomDatabase() {
    abstract fun getPhotoDao(): RedditDAO
}