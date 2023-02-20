package com.arnava.ur.data.repository

import com.arnava.ur.data.db.DbComment
import com.arnava.ur.data.db.DbPost
import com.arnava.ur.data.db.RedditDAO
import javax.inject.Inject

class DbRepository @Inject constructor (private val redditDAO: RedditDAO) {
    fun getPosts() = redditDAO.getAllPosts()
    suspend fun addPost(dbPost: DbPost) = redditDAO.insertPost(dbPost)
    suspend fun deletePost(postId: String) = redditDAO.deletePost(postId)

    fun getComments() = redditDAO.getAllComments()
    suspend fun addComment(dbComment: DbComment) = redditDAO.insertComment(dbComment)
    suspend fun deleteComment(commentId: String) = redditDAO.deleteComment(commentId)
}