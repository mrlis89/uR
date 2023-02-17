package com.arnava.ur.data.model.users
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class AccountInfo(
    @Json(name = "is_employee")
    val isEmployee: Boolean,
    @Json(name = "seen_layout_switch")
    val seenLayoutSwitch: Boolean,
    @Json(name = "has_visited_new_profile")
    val hasVisitedNewProfile: Boolean,
    @Json(name = "pref_no_profanity")
    val prefNoProfanity: Boolean,
    @Json(name = "has_external_account")
    val hasExternalAccount: Boolean,
    @Json(name = "pref_geopopular")
    val prefGeopopular: String,
    @Json(name = "seen_redesign_modal")
    val seenRedesignModal: Boolean,
    @Json(name = "pref_show_trending")
    val prefShowTrending: Boolean,
    @Json(name = "subreddit")
    val subreddit: Subreddit,
    @Json(name = "pref_show_presence")
    val prefShowPresence: Boolean,
    @Json(name = "snoovatar_img")
    val snoovatarImg: String,
    @Json(name = "snoovatar_size")
    val snoovatarSize: List<Int>,
    @Json(name = "gold_expiration")
    val goldExpiration: Any?,
    @Json(name = "has_gold_subscription")
    val hasGoldSubscription: Boolean,
    @Json(name = "is_sponsor")
    val isSponsor: Boolean,
    @Json(name = "num_friends")
    val numFriends: Int,
    @Json(name = "features")
    val features: Features,
    @Json(name = "can_edit_name")
    val canEditName: Boolean,
    @Json(name = "verified")
    val verified: Boolean,
    @Json(name = "new_modmail_exists")
    val newModmailExists: Any?,
    @Json(name = "pref_autoplay")
    val prefAutoplay: Boolean,
    @Json(name = "coins")
    val coins: Int,
    @Json(name = "has_paypal_subscription")
    val hasPaypalSubscription: Boolean,
    @Json(name = "has_subscribed_to_premium")
    val hasSubscribedToPremium: Boolean,
    @Json(name = "id")
    val id: String,
    @Json(name = "has_stripe_subscription")
    val hasStripeSubscription: Boolean,
    @Json(name = "oauth_client_id")
    val oauthClientId: String,
    @Json(name = "can_create_subreddit")
    val canCreateSubreddit: Boolean,
    @Json(name = "over_18")
    val over18: Boolean,
    @Json(name = "is_gold")
    val isGold: Boolean,
    @Json(name = "is_mod")
    val isMod: Boolean,
    @Json(name = "awarder_karma")
    val awarderKarma: Int,
    @Json(name = "suspension_expiration_utc")
    val suspensionExpirationUtc: Any?,
    @Json(name = "has_verified_email")
    val hasVerifiedEmail: Boolean,
    @Json(name = "is_suspended")
    val isSuspended: Boolean,
    @Json(name = "pref_video_autoplay")
    val prefVideoAutoplay: Boolean,
    @Json(name = "has_android_subscription")
    val hasAndroidSubscription: Boolean,
    @Json(name = "in_redesign_beta")
    val inRedesignBeta: Boolean,
    @Json(name = "icon_img")
    val iconImg: String,
    @Json(name = "has_mod_mail")
    val hasModMail: Boolean,
    @Json(name = "pref_nightmode")
    val prefNightmode: Boolean,
    @Json(name = "awardee_karma")
    val awardeeKarma: Int,
    @Json(name = "hide_from_robots")
    val hideFromRobots: Boolean,
    @Json(name = "password_set")
    val passwordSet: Boolean,
    @Json(name = "link_karma")
    val linkKarma: Int,
    @Json(name = "force_password_reset")
    val forcePasswordReset: Boolean,
    @Json(name = "total_karma")
    val totalKarma: Int,
    @Json(name = "seen_give_award_tooltip")
    val seenGiveAwardTooltip: Boolean,
    @Json(name = "inbox_count")
    val inboxCount: Int,
    @Json(name = "seen_premium_adblock_modal")
    val seenPremiumAdblockModal: Boolean,
    @Json(name = "pref_top_karma_subreddits")
    val prefTopKarmaSubreddits: Boolean,
    @Json(name = "has_mail")
    val hasMail: Boolean,
    @Json(name = "pref_show_snoovatar")
    val prefShowSnoovatar: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "pref_clickgadget")
    val prefClickgadget: Int,
    @Json(name = "created")
    val created: Double,
    @Json(name = "gold_creddits")
    val goldCreddits: Int,
    @Json(name = "created_utc")
    val createdUtc: Double,
    @Json(name = "has_ios_subscription")
    val hasIosSubscription: Boolean,
    @Json(name = "pref_show_twitter")
    val prefShowTwitter: Boolean,
    @Json(name = "in_beta")
    val inBeta: Boolean,
    @Json(name = "comment_karma")
    val commentKarma: Int,
    @Json(name = "accept_followers")
    val acceptFollowers: Boolean,
    @Json(name = "has_subscribed")
    val hasSubscribed: Boolean,
    @Json(name = "linked_identities")
    val linkedIdentities: List<Any>,
    @Json(name = "seen_subreddit_chat_ftux")
    val seenSubredditChatFtux: Boolean
)

@JsonClass(generateAdapter = true)
data class Subreddit(
    @Json(name = "default_set")
    val defaultSet: Boolean,
    @Json(name = "user_is_contributor")
    val userIsContributor: Boolean,
    @Json(name = "banner_img")
    val bannerImg: String,
    @Json(name = "restrict_posting")
    val restrictPosting: Boolean,
    @Json(name = "user_is_banned")
    val userIsBanned: Boolean,
    @Json(name = "free_form_reports")
    val freeFormReports: Boolean,
    @Json(name = "community_icon")
    val communityIcon: Any?,
    @Json(name = "show_media")
    val showMedia: Boolean,
    @Json(name = "icon_color")
    val iconColor: String,
    @Json(name = "user_is_muted")
    val userIsMuted: Any?,
    @Json(name = "display_name")
    val displayName: String,
    @Json(name = "header_img")
    val headerImg: Any?,
    @Json(name = "title")
    val title: String,
    @Json(name = "coins")
    val coins: Int,
    @Json(name = "previous_names")
    val previousNames: List<Any>,
    @Json(name = "over_18")
    val over18: Boolean,
    @Json(name = "icon_size")
    val iconSize: List<Int>,
    @Json(name = "primary_color")
    val primaryColor: String,
    @Json(name = "icon_img")
    val iconImg: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "allowed_media_in_comments")
    val allowedMediaInComments: List<Any>,
    @Json(name = "submit_link_label")
    val submitLinkLabel: String,
    @Json(name = "header_size")
    val headerSize: Any?,
    @Json(name = "restrict_commenting")
    val restrictCommenting: Boolean,
    @Json(name = "subscribers")
    val subscribers: Int,
    @Json(name = "submit_text_label")
    val submitTextLabel: String,
    @Json(name = "is_default_icon")
    val isDefaultIcon: Boolean,
    @Json(name = "link_flair_position")
    val linkFlairPosition: String,
    @Json(name = "display_name_prefixed")
    val displayNamePrefixed: String,
    @Json(name = "key_color")
    val keyColor: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "is_default_banner")
    val isDefaultBanner: Boolean,
    @Json(name = "url")
    val url: String,
    @Json(name = "quarantine")
    val quarantine: Boolean,
    @Json(name = "banner_size")
    val bannerSize: Any?,
    @Json(name = "user_is_moderator")
    val userIsModerator: Boolean,
    @Json(name = "accept_followers")
    val acceptFollowers: Boolean,
    @Json(name = "public_description")
    val publicDescription: String,
    @Json(name = "link_flair_enabled")
    val linkFlairEnabled: Boolean,
    @Json(name = "disable_contributor_requests")
    val disableContributorRequests: Boolean,
    @Json(name = "subreddit_type")
    val subredditType: String,
    @Json(name = "user_is_subscriber")
    val userIsSubscriber: Boolean
)

@JsonClass(generateAdapter = true)
data class Features(
    @Json(name = "modmail_harassment_filter")
    val modmailHarassmentFilter: Boolean,
    @Json(name = "mod_service_mute_writes")
    val modServiceMuteWrites: Boolean,
    @Json(name = "promoted_trend_blanks")
    val promotedTrendBlanks: Boolean,
    @Json(name = "show_amp_link")
    val showAmpLink: Boolean,
    @Json(name = "is_email_permission_required")
    val isEmailPermissionRequired: Boolean,
    @Json(name = "mod_awards")
    val modAwards: Boolean,
    @Json(name = "mweb_xpromo_revamp_v3")
    val mwebXpromoRevampV3: MwebXpromoRevampV3,
    @Json(name = "mweb_xpromo_revamp_v2")
    val mwebXpromoRevampV2: MwebXpromoRevampV3,
    @Json(name = "awards_on_streams")
    val awardsOnStreams: Boolean,
    @Json(name = "mweb_xpromo_modal_listing_click_daily_dismissible_ios")
    val mwebXpromoModalListingClickDailyDismissibleIos: Boolean,
    @Json(name = "chat_subreddit")
    val chatSubreddit: Boolean,
    @Json(name = "cookie_consent_banner")
    val cookieConsentBanner: Boolean,
    @Json(name = "modlog_copyright_removal")
    val modlogCopyrightRemoval: Boolean,
    @Json(name = "do_not_track")
    val doNotTrack: Boolean,
    @Json(name = "images_in_comments")
    val imagesInComments: Boolean,
    @Json(name = "mod_service_mute_reads")
    val modServiceMuteReads: Boolean,
    @Json(name = "chat_user_settings")
    val chatUserSettings: Boolean,
    @Json(name = "use_pref_account_deployment")
    val usePrefAccountDeployment: Boolean,
    @Json(name = "mweb_xpromo_interstitial_comments_ios")
    val mwebXpromoInterstitialCommentsIos: Boolean,
    @Json(name = "mweb_sharing_clipboard")
    val mwebSharingClipboard: MwebSharingClipboard,
    @Json(name = "premium_subscriptions_table")
    val premiumSubscriptionsTable: Boolean,
    @Json(name = "mweb_xpromo_interstitial_comments_android")
    val mwebXpromoInterstitialCommentsAndroid: Boolean,
    @Json(name = "crowd_control_for_post")
    val crowdControlForPost: Boolean,
    @Json(name = "mweb_nsfw_xpromo")
    val mwebNsfwXpromo: MwebNsfwXpromo,
    @Json(name = "mweb_xpromo_modal_listing_click_daily_dismissible_android")
    val mwebXpromoModalListingClickDailyDismissibleAndroid: Boolean,
    @Json(name = "chat_group_rollout")
    val chatGroupRollout: Boolean,
    @Json(name = "resized_styles_images")
    val resizedStylesImages: Boolean,
    @Json(name = "noreferrer_to_noopener")
    val noreferrerToNoopener: Boolean,
    @Json(name = "expensive_coins_package")
    val expensiveCoinsPackage: Boolean
)

@JsonClass(generateAdapter = true)
data class MwebXpromoRevampV3(
    @Json(name = "owner")
    val owner: String,
    @Json(name = "variant")
    val variant: String,
    @Json(name = "experiment_id")
    val experimentId: Int
)

@JsonClass(generateAdapter = true)
data class MwebSharingClipboard(
    @Json(name = "owner")
    val owner: String,
    @Json(name = "variant")
    val variant: String,
    @Json(name = "experiment_id")
    val experimentId: Int
)

@JsonClass(generateAdapter = true)
data class MwebNsfwXpromo(
    @Json(name = "owner")
    val owner: String,
    @Json(name = "variant")
    val variant: String,
    @Json(name = "experiment_id")
    val experimentId: Int
)