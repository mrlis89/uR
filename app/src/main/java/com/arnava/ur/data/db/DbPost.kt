package com.arnava.ur.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postTable")
data class DbPost(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "subredditName")
    val subredditName: String,
    @ColumnInfo(name = "postText")
    val postText: String,
    @ColumnInfo(name = "postAuthor")
    val postAuthor: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
)
