package com.indie.apps.newyorktimestories.ui.model

import com.indie.apps.newyorktimestories.data.model.Multimedia

data class UIArticle(
    val id:Long,
    val title: String,
    val author: String,
    val images: List<Multimedia>?,
    val details: String,
    val url: String
)