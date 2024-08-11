package com.indie.apps.newyorktimestories.ui.story_list

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.indie.apps.newyorktimestories.data.api.RetrofitInstance
import com.indie.apps.newyorktimestories.data.db.DatabaseInstance
import com.indie.apps.newyorktimestories.data.repository.StoryRepository
import com.indie.apps.newyorktimestories.data.repository.StoryRepositoryImpl
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class StoryListViewModel (
    private val getStoriesFromSectionUseCase : GetStoriesFromSectionUseCase
) : ViewModel() {

    /*val uiState = getStoriesFromSectionUseCase
        .loadData("home")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Resource.Loading())*/

    val currentSection = MutableStateFlow("home")

    private val trigger = MutableSharedFlow<Unit>(replay = 1)
    val uiState = trigger
        .flatMapLatest {
            getStoriesFromSectionUseCase
                .loadData("",currentSection.value)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Resource.Loading())
    init {
        getDataWithSection()
    }

    private fun getDataWithSection() {
        viewModelScope.launch {
            trigger.emit(Unit)
        }
    }

    fun setSection(section : String){
        val searchtext = section.lowercase(Locale.ROOT)
        if(currentSection.value != searchtext) {
            currentSection.value = searchtext
            getDataWithSection()
        }

    }
}

class StoryListViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        val storyRepo = StoryRepositoryImpl(RetrofitInstance.api, DatabaseInstance.database.getArticleDao())
        return StoryListViewModel(GetStoriesFromSectionUseCase(storyRepo)) as T
    }
}
