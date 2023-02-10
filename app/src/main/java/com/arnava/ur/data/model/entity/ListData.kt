package com.arnava.ur.data.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListData(
    @Json(name = "modhash")
    val modhash: String? = null,
    @Json(name = "children")
    val subreddits: List<Subreddit>? = null,
    @Json(name = "after")
    val after: String? = null,
    @Json(name = "before")
    val before: Any? = null
)