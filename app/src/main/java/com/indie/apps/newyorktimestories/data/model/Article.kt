package com.indie.apps.newyorktimestories.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @SerializedName("abstract")
    val details: String,
    val kicker: String,
    val multimedia: List<Multimedia>?,
    val section: String,
    val title: String,
    val uri: String,
    val url: String
)

fun Article.toUIArticle() = UIArticle(id?: 0,title, kicker, multimedia, details)
