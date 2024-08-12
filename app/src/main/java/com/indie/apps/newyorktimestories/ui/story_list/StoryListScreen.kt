package com.indie.apps.newyorktimestories.ui.story_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.ui.common.ErrorScreen
import com.indie.apps.newyorktimestories.ui.common.LoadingScreen
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.ui.story_list.component.StoryCardListItem
import com.indie.apps.newyorktimestories.ui.story_list.component.StoryListItem
import com.indie.apps.newyorktimestories.ui.story_list.component.StoryListTopBar
import com.indie.apps.newyorktimestories.util.ErrorMessage
import com.indie.apps.newyorktimestories.util.Resource

@Composable
fun StoryListScreen() {
    var viewModel: StoryListViewModel = viewModel(
        factory = StoryListViewModelFactory()
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentFilterText by viewModel.currentSection.collectAsStateWithLifecycle()
    val filterList = stringArrayResource(id = R.array.section).toList()

    var enabledList by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            StoryListTopBar(
                onTextChange = viewModel::searchData,
                options = filterList,
                onFilterSelect = viewModel::setSection,
                selectedFilterText = currentFilterText,
                enabledList = enabledList,
                onListChange = {
                    enabledList = !enabledList
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding()))

        ) {

            when (uiState) {
                is Resource.Loading -> {
                    LoadingScreen()
                }

                is Resource.Error -> {
                    ErrorScreen(uiState.message)
                }

                is Resource.Success -> {
                    if (uiState.data.isNullOrEmpty()) {
                        ErrorScreen(ErrorMessage.N0_DATA_FOUND.message)
                    } else {
                        if (enabledList) {
                            StoryListScreenData(
                                list = uiState.data!!,
                                onItemSelect = {},
                            )
                        } else {
                            StoryCardListScreenData(
                                list = uiState.data!!,
                                onItemSelect = {},
                            )
                        }

                    }
                }
            }
        }
    }


}

@Composable
fun StoryListScreenData(
    onItemSelect: () -> Unit,
    list: List<UIArticle>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.inner_padding))
    ) {
        items(list) { article ->
            StoryListItem(
                uiArticle = article,
                onItemSelect = onItemSelect
            )
        }
    }
}

@Composable
fun StoryCardListScreenData(
    onItemSelect: () -> Unit,
    list: List<UIArticle>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.inner_padding))
    ) {
        items(list.size) { index ->
            StoryCardListItem(
                uiArticle = list[index],
                onItemSelect = onItemSelect
            )
        }
    }
}