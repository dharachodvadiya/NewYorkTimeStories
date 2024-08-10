package com.indie.apps.newyorktimestories.usecase

import com.indie.apps.newyorktimestories.data.model.ApiResponse
import com.indie.apps.newyorktimestories.data.model.Article
import com.indie.apps.newyorktimestories.data.repository.StoryRepository
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException

class GetStoriesFromSectionUseCase(
    private val storyRepository: StoryRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun loadData(section: String): Flow<Resource<List<Article>>> {
        return flow{
            emit(Resource.Loading())
            try {
                val response = storyRepository.getTopStories(section)
                emit(handleLoadStoryResponse(response))
            } catch(t: Throwable) {
                when(t) {
                    is IOException -> emit(Resource.Error("Network Failure"))
                    else -> emit(Resource.Error("Conversion Error"))
                }
            }
        }.flowOn(dispatcher)
    }

}

private fun handleLoadStoryResponse(response: Response<ApiResponse>) : Resource<List<Article>> {
    if(response.isSuccessful) {
        response.body()?.let { resultResponse ->
            return  Resource.Success(resultResponse.results)
        }
    }
    return Resource.Error(response.message())
}