package com.arnava.ur.data.model.users
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "kind")
    val kind: String,
    @Json(name = "data")
    val userInfoData: UserInfoData
)

@JsonClass(generateAdapter = true)
data class UserInfoData(
    @Json(name = "is_employee")
    val isEmployee: Boolean,
    @Json(name = "is_friend")
    val isFriend: Boolean,
    @Json(name = "subreddit")
    val subreddit: Subreddit,
    @Json(name = "snoovatar_size")
    val snoovatarSize: List<Int>,
    @Json(name = "awardee_karma")
    val awardeeKarma: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "verified")
    val verified: Boolean,
    @Json(name = "is_gold")
    val isGold: Boolean,
    @Json(name = "is_mod")
    val isMod: Boolean,
    @Json(name = "awarder_karma")
    val awarderKarma: Int,
    @Json(name = "has_verified_email")
    val hasVerifiedEmail: Boolean,
    @Json(name = "icon_img")
    val iconImg: String,
    @Json(name = "hide_from_robots")
    val hideFromRobots: Boolean,
    @Json(name = "link_karma")
    val linkKarma: Int,
    @Json(name = "pref_show_snoovatar")
    val prefShowSnoovatar: Boolean,
    @Json(name = "is_blocked")
    val isBlocked: Boolean,
    @Json(name = "total_karma")
    val totalKarma: Int,
    @Json(name = "accept_chats")
    val acceptChats: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "created")
    val created: Double,
    @Json(name = "created_utc")
    val createdUtc: Double,
    @Json(name = "snoovatar_img")
    val snoovatarImg: String,
    @Json(name = "comment_karma")
    val commentKarma: Int,
    @Json(name = "accept_followers")
    val acceptFollowers: Boolean,
    @Json(name = "has_subscribed")
    val hasSubscribed: Boolean,
    @Json(name = "accept_pms")
    val acceptPms: Boolean
)
