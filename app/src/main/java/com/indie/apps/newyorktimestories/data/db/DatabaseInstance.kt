package com.indie.apps.newyorktimestories.data.db

import com.indie.apps.newyorktimestories.MyApplication
import com.indie.apps.newyorktimestories.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DatabaseInstance {

    companion object{

        val database: ArticleDatabase by lazy {
            ArticleDatabase(MyApplication.instance)
        }
    }
}