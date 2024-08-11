package com.indie.apps.newyorktimestories.data.repository

import com.indie.apps.newyorktimestories.data.model.ApiResponse
import com.indie.apps.newyorktimestories.data.model.Article
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface StoryRepository {
    suspend fun getTopStories(section: String): Response<ApiResponse>

    suspend fun searchRecordsFromSection(search:String,section: String): Flow<Resource<List<Article>>>

    suspend fun getRecordsFromSection(section: String): Flow<Resource<List<Article>>>
}