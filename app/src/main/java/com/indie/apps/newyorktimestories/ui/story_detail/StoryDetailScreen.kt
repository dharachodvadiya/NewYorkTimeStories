package com.indie.apps.newyorktimestories.ui.story_detail

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.ui.story_list.StoryListViewModel

@Composable
fun StoryDetailScreen() {
    var viewModel: StoryDetailViewModel = viewModel(
        factory = StoryDetailViewModel.Factory
    )
}