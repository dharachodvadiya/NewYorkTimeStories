package com.indie.apps.newyorktimestories.ui.story_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.data.model.ApiResponse
import com.indie.apps.newyorktimestories.util.Resource

@Composable
fun StoryListScreen() {
    var viewModel: StoryListViewModel = viewModel(
        factory = StoryListViewModelFactory()
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

   when(uiState){
       is Resource.Loading -> {}
       is Resource.Error -> {}
       is Resource.Success -> {}
   }

}