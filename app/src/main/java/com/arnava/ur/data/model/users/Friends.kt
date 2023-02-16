package com.arnava.ur.data.model.users
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Friends(
    @Json(name = "kind")
    val kind: String,
    @Json(name = "data")
    val friendsData: FriendsData
)

@JsonClass(generateAdapter = true)
data class FriendsData(
    @Json(name = "children")
    val friends: List<Friend>
)

@JsonClass(generateAdapter = true)
data class Friend(
    @Json(name = "date")
    val date: Double,
    @Json(name = "rel_id")
    val relId: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "id")
    val id: String
)