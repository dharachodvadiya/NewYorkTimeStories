package com.indie.apps.newyorktimestories.ui.story_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.data.model.Article
import com.indie.apps.newyorktimestories.ui.story_list.component.StoryListItem
import com.indie.apps.newyorktimestories.ui.story_list.component.StoryListTopBar
import com.indie.apps.newyorktimestories.util.Resource

@Composable
fun StoryListScreen() {
    var viewModel: StoryListViewModel = viewModel(
        factory = StoryListViewModelFactory()
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is Resource.Loading -> {}
        is Resource.Error -> {}
        is Resource.Success -> {
            uiState.data?.let {
                StoryListScreenData(
                    list = it
                )
            }
        }
    }
}

@Composable
fun StoryListScreenData(
    list: List<Article>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            StoryListTopBar(
                onTextChange = {},
                options = stringArrayResource(id = R.array.section).toList()
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier.padding(paddingValues = innerPadding)
        ) {
            LazyColumn{
                items(list) { movie ->
                    StoryListItem()
                }
            }
        }
    }
}