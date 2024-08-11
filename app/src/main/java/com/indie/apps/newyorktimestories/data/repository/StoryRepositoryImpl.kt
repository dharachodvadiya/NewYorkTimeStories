package com.indie.apps.newyorktimestories.data.repository

import com.indie.apps.newyorktimestories.data.api.ApiService

class StoryRepositoryImpl(private val apiService: ApiService) :
    StoryRepository {
    override suspend fun getTopStories(section: String) = apiService.getTopStories(section)
}