package com.indie.apps.newyorktimestories.usecase

import com.indie.apps.newyorktimestories.data.model.toUIArticle
import com.indie.apps.newyorktimestories.data.repository.StoryRepository
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.util.ErrorMessage
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

class GetStoriesFromIdUseCase(
    private val storyRepository: StoryRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun loadData(id: Long): Flow<Resource<UIArticle>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = storyRepository.getRecordsFromId(id)
                emit(Resource.Success(response.toUIArticle()))
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> emit(Resource.Error(ErrorMessage.NO_NETWORK.message))
                    else -> emit(Resource.Error(t.localizedMessage))
                }
            }
        }.flowOn(dispatcher)
    }
}