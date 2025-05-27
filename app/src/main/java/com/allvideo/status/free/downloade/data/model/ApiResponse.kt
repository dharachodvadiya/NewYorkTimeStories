package com.allvideo.status.free.downloade.data.model

data class ApiResponse(
    val copyright: String,
    val last_updated: String,
    val num_results: Int,
    val results: List<Article>,
    val section: String,
    val status: String
)