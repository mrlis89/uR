package com.arnava.ur.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentTable")
data class DbComment(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "subredditName")
    val subredditName: String,
    @ColumnInfo(name = "commentText")
    val commentText: String,
    @ColumnInfo(name = "commentAuthor")
    val commentAuthor: String,
    @ColumnInfo(name = "likesCount")
    val likesCount: Int,
)
