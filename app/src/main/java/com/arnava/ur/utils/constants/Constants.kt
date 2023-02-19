package com.arnava.ur.utils.constants

import net.openid.appauth.ResponseTypeValues

const val AUTH_URI = "https://www.reddit.com/api/v1/authorize.compact?"
const val TOKEN_URI = "https://www.reddit.com/api/v1/access_token"
const val RESPONSE_TYPE = ResponseTypeValues.CODE
const val SCOPE = "identity account edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread"
const val CLIENT_ID = "IIxOWiZZX8zApbYWOHUPXA"
const val REDIRECT_URL = "com.ur.oauth://ur.com/callback"

const val THING_DATA = "thingData"
const val USER_NAME = "userName"
const val DATE_FORMAT = "MM/dd, HH:mm"