package com.allvideo.status.free.downloade.ui.story_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.allvideo.status.free.downloade.data.api.RetrofitInstance
import com.allvideo.status.free.downloade.data.db.DatabaseInstance
import com.allvideo.status.free.downloade.data.repository.StoryRepositoryImpl
import com.allvideo.status.free.downloade.usecase.GetStoriesFromSectionUseCase
import com.allvideo.status.free.downloade.usecase.SearchStoriesFromSectionUseCase
import com.allvideo.status.free.downloade.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class StoryListViewModel (
    private val getStoriesFromSectionUseCase : GetStoriesFromSectionUseCase,
    private val searchStoriesFromSectionUseCase : SearchStoriesFromSectionUseCase,
) : ViewModel() {

    val currentSection = MutableStateFlow("home")
    private var searchQuery = ""
    private var isSearch  = false

    private val trigger = MutableSharedFlow<Unit>(replay = 1)
    val uiState = trigger
        .flatMapLatest {
            if(isSearch)
            {
                isSearch = false
                searchStoriesFromSectionUseCase
                    .loadData(searchQuery,currentSection.value)
            }else{
                getStoriesFromSectionUseCase
                    .loadData(currentSection.value)
            }

        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), Resource.Loading())
    init {
        getDataWithSection()
    }

    fun getDataWithSection() {
        viewModelScope.launch {
            trigger.emit(Unit)
        }
    }

    fun searchData(searchQuery : String){
        isSearch = true
        this.searchQuery = searchQuery
        getDataWithSection()
    }

    fun setSection(section : String){
        isSearch = false
        searchQuery = ""
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
        return StoryListViewModel(
            GetStoriesFromSectionUseCase(storyRepo),
            SearchStoriesFromSectionUseCase(storyRepo)
        ) as T
    }
}
