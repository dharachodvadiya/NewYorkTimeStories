package com.allvideo.status.free.downloade.data.repository

import com.allvideo.status.free.downloade.data.model.ApiResponse
import com.allvideo.status.free.downloade.data.model.Article
import com.allvideo.status.free.downloade.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface StoryRepository {
    suspend fun getTopStories(section: String): Response<ApiResponse>

    suspend fun searchRecordsFromSection(search:String,section: String): Flow<Resource<List<Article>>>

    suspend fun getRecordsFromSection(section: String): Flow<Resource<List<Article>>>
    suspend fun getRecordsFromId(id : Long): Article
}