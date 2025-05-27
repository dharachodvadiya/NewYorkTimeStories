package com.allvideo.status.free.downloade.usecase

import com.allvideo.status.free.downloade.data.model.toUIArticle
import com.allvideo.status.free.downloade.data.repository.StoryRepository
import com.allvideo.status.free.downloade.ui.model.UIArticle
import com.allvideo.status.free.downloade.util.ErrorMessage
import com.allvideo.status.free.downloade.util.Resource
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