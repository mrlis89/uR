package com.arnava.ur.data.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListData(
    @Json(name = "modhash")
    val modhash: String?,
    @Json(name = "children")
    val posts: List<Post>?,
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: Any?
)