package com.allvideo.status.free.downloade.data.api

import com.allvideo.status.free.downloade.data.model.ApiResponse
import com.allvideo.status.free.downloade.util.Constant
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
