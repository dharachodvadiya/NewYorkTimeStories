package com.indie.apps.newyorktimestories.data.repository

import com.indie.apps.newyorktimestories.data.model.ApiResponse
import retrofit2.Response

interface StoryRepository {
    suspend fun getTopStories(section: String): Response<ApiResponse>
}