package com.indie.apps.newyorktimestories.data.model

data class Result(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<Article>,
    val section: String,
    val status: String
)