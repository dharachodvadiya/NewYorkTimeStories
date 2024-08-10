package com.indie.apps.newyorktimestories.ui.story_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.indie.apps.newyorktimestories.ui.story_list.StoryListViewModel

class StoryDetailViewModel : ViewModel(){

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                StoryDetailViewModel()
            }
        }
    }
}