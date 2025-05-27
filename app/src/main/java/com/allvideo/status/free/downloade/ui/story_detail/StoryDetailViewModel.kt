package com.allvideo.status.free.downloade.ui.story_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.allvideo.status.free.downloade.data.api.RetrofitInstance
import com.allvideo.status.free.downloade.data.db.DatabaseInstance
import com.allvideo.status.free.downloade.data.repository.StoryRepositoryImpl
import com.allvideo.status.free.downloade.ui.model.UIArticle
import com.allvideo.status.free.downloade.usecase.GetStoriesFromIdUseCase
import com.allvideo.status.free.downloade.util.Constant
import com.allvideo.status.free.downloade.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoryDetailViewModel(
    private val getStoriesFromIdUseCase: GetStoriesFromIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<UIArticle>>(Resource.Loading())
    val uiState = _uiState.asStateFlow()


    init {
        savedStateHandle.get<String>(Constant.PARAM_ARTICLE_ID)?.let { id ->
            getArticle(id.toLong())
        }
    }

    fun getArticle(id: Long) {
        viewModelScope.launch {
            getStoriesFromIdUseCase.loadData(id).collect() { result ->
                _uiState.emit(result)
            }
        }
    }
}

class StoryDetailViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val storyRepo =
            StoryRepositoryImpl(RetrofitInstance.api, DatabaseInstance.database.getArticleDao())
        val usecase = GetStoriesFromIdUseCase(storyRepo)
        return StoryDetailViewModel(usecase, extras.createSavedStateHandle()) as T
    }
}