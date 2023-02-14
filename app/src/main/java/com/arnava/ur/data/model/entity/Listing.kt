package com.arnava.ur.data.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Listing (

    @Json(name = "kind")
    val kind: String?,
    @Json(name = "data")
    val listData: ListData?

) : Parcelable
