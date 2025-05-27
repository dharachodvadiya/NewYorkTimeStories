package com.allvideo.status.free.downloade.data.db

import com.allvideo.status.free.downloade.MyApplication

class DatabaseInstance {

    companion object{

        val database: ArticleDatabase by lazy {
            ArticleDatabase(MyApplication.instance)
        }
    }
}