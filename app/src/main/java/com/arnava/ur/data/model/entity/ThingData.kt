package com.arnava.ur.data.model.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ThingData(
        @Json(name = "banner_img")
        val bannerImg: String?,
        @Json(name = "user_sr_theme_enabled")
        val userSrThemeEnabled: Boolean?,
        @Json(name = "submit_text_html")
        val submitTextHtml: String?,
        @Json(name = "wiki_enabled")
        val wikiEnabled: Boolean?,
        @Json(name = "show_media")
        val showMedia: Boolean?,
        @Json(name = "id")
        val id: String?,
        @Json(name = "selftext")
        val selftext: String?,
        @Json(name = "display_name")
        val displayName: String?,
        @Json(name = "header_img")
        val headerImg: String?,
        @Json(name = "description_html")
        val descriptionHtml: String?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "author")
        val author: String?,
        @Json(name = "collapse_deleted_comments")
        val collapseDeletedComments: Boolean?,
        @Json(name = "over18")
        val over18: Boolean?,
        @Json(name = "public_description_html")
        val publicDescriptionHtml: String?,
        @Json(name = "spoilers_enabled")
        val spoilersEnabled: Boolean?,
        @Json(name = "icon_size")
        val iconSize: List<Int>?,
        @Json(name = "icon_img")
        val iconImg: String?,
        @Json(name = "header_title")
        val headerTitle: String?,
        @Json(name = "description")
        val description: String?,
        @Json(name = "public_traffic")
        val publicTraffic: Boolean?,
        @Json(name = "header_size")
        val headerSize: List<Int>?,
        @Json(name = "subscribers")
        val subscribers: Int?,
        @Json(name = "lang")
        val lang: String?,
        @Json(name = "key_color")
        val keyColor: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "created")
        val created: Double?,
        @Json(name = "url")
        val url: String?,
        @Json(name = "quarantine")
        val quarantine: Boolean?,
        @Json(name = "hide_ads")
        val hideAds: Boolean?,
        @Json(name = "created_utc")
        val createdUtc: Double?,
        @Json(name = "public_description")
        val publicDescription: String?,
        @Json(name = "show_media_preview")
        val showMediaPreview: Boolean?,
        @Json(name = "comment_score_hide_mins")
        val commentScoreHideMins: Int?,
        @Json(name = "subreddit_type")
        val subredditType: String?,
        @Json(name = "submission_type")
        val submissionType: String?,
        @Json(name = "user_is_subscriber")
        val userIsSubscriber: Boolean?
) : Parcelable