package com.indie.apps.newyorktimestories.ui.story_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.indie.apps.newyorktimestories.data.api.RetrofitInstance
import com.indie.apps.newyorktimestories.data.repository.StoryRepository
import com.indie.apps.newyorktimestories.data.repository.StoryRepositoryImpl
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StoryListViewModel (
    private val getStoriesFromSectionUseCase : GetStoriesFromSectionUseCase
) : ViewModel() {

    val uiState = getStoriesFromSectionUseCase
        .loadData("hello")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Resource.Loading())
}

class StoryListViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        val storyRepo = StoryRepositoryImpl(RetrofitInstance.api)
        return StoryListViewModel(GetStoriesFromSectionUseCase(storyRepo)) as T
    }
}
