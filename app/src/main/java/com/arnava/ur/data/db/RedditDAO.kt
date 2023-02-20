package com.arnava.ur.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RedditDAO {

    @Query("SELECT * FROM postTable")
    fun getAllPosts(): Flow<List<DbPost>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(photoDb: DbPost): Long

    @Query("DELETE FROM postTable")
    suspend fun dropPosts()


    @Query("SELECT * FROM commentTable")
    fun getAllComments(): Flow<List<DbComment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComment(dbComment: DbComment): Long

    @Query("DELETE FROM commentTable")
    suspend fun dropComments()
}