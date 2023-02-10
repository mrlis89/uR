package com.arnava.ur.data.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.thirdmono.reddit.data.entity.SubRedditData

@JsonClass(generateAdapter = true)
data class Subreddit (
    @Json(name = "kind")
    val kind: String? = null,
    @Json(name = "data")
    val data: SubRedditData? = null
)
