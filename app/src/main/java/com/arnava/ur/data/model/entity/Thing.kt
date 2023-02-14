package com.arnava.ur.data.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Thing(
    @Json(name = "kind")
    val kind: String?,
    @Json(name = "data")
    val data: ThingData?,
    var nestingLevel: Int? = 0

) : Parcelable {
    @IgnoredOnParcel
    val replies = data?.replies?.listData?.things

    fun putChildrenTo(list: MutableList<Thing>, nestingLevel: Int) {
        this.nestingLevel = nestingLevel + 1
         if (kind != "more") {
            list.add(this)
            replies?.forEach { it.putChildrenTo(list, nestingLevel + 1) }
        }
    }
}
