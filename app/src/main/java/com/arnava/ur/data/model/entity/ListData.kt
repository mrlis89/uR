package com.arnava.ur.data.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ListData(
    @Json(name = "modhash")
    val modhash: String?,
    @Json(name = "children")
    val things: List<Thing>?,
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: String?
) : Parcelable