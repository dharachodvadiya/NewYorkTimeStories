package com.indie.apps.newyorktimestories.data.api

import com.indie.apps.newyorktimestories.data.model.ApiResponse
import com.indie.apps.newyorktimestories.util.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/{section}.json")
    suspend fun getTopStories(
        @Path("section") section: String,
        @Query("api-key") apiKey: String = Constant.API_KEY
    ): Response<ApiResponse>
}
