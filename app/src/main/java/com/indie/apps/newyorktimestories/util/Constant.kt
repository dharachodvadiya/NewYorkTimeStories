package com.indie.apps.newyorktimestories.util

import com.indie.apps.newyorktimestories.BuildConfig

object Constant {
    val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://api.nytimes.com/svc/topstories/"
    const val SEARCH_NEWS_TIME_DELAY = 500L
    const val QUERY_PAGE_SIZE = 20
}
