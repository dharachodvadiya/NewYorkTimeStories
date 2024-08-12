package com.indie.apps.newyorktimestories.ui.story_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.indie.apps.newyorktimestories.data.api.RetrofitInstance
import com.indie.apps.newyorktimestories.data.db.DatabaseInstance
import com.indie.apps.newyorktimestories.data.repository.StoryRepositoryImpl
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.ui.story_list.StoryListViewModel
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromIdUseCase
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.usecase.SearchStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.util.Constant
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class StoryDetailViewModel(
    private val getStoriesFromIdUseCase : GetStoriesFromIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _uiState = MutableStateFlow<Resource<UIArticle>>(Resource.Loading())
    val uiState = _uiState.asStateFlow()


    init {
        savedStateHandle.get<String>(Constant.PARAM_ARTICLE_ID)?.let { id ->
            getArticle(id.toLong())
        }
    }

    private fun getArticle(id: Long) {
        getStoriesFromIdUseCase.loadData(id).onEach { result ->
            _uiState.emit(result)
        }.launchIn(viewModelScope)
    }
}

class StoryDetailViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras) : T {
        val storyRepo = StoryRepositoryImpl(RetrofitInstance.api, DatabaseInstance.database.getArticleDao())
        val usecase = GetStoriesFromIdUseCase(storyRepo)
        return StoryDetailViewModel(usecase,extras.createSavedStateHandle()) as T
    }
}