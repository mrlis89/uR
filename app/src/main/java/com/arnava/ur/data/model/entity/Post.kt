package com.arnava.ur.data.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post (
    @Json(name = "kind")
    val kind: String?,
    @Json(name = "data")
    val data: PostData?
)
