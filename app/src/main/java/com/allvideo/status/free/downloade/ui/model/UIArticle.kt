package com.allvideo.status.free.downloade.ui.model

import com.allvideo.status.free.downloade.data.model.Multimedia

data class UIArticle(
    val id:Long,
    val title: String,
    val author: String,
    val images: List<Multimedia>?,
    val details: String,
    val url: String
)