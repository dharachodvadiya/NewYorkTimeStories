package com.indie.apps.newyorktimestories.data.api

import com.indie.apps.newyorktimestories.util.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(ApiService::class.java)
        }
    }
}