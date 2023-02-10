package com.arnava.ur.data.model.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listing (

    @Json(name = "kind")
    val kind: String?,
    @Json(name = "data")
    val listData: ListData?

)
