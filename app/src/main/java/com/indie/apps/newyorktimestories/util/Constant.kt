package com.indie.apps.newyorktimestories.util

import com.indie.apps.newyorktimestories.BuildConfig

object Constant {
    val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.nytimes.com/svc/topstories/"
    const val PARAM_ARTICLE_ID = "id"
    const val PREF_ARTICLE_TITLE = "title"
}
