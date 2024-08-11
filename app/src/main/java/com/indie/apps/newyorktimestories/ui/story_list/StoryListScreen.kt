package com.indie.apps.newyorktimestories.ui.story_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.ui.common.ErrorScreen
import com.indie.apps.newyorktimestories.ui.common.LoadingScreen
import com.indie.apps.newyorktimestories.ui.model.UIArticle
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

    Scaffold(
        topBar = {
            StoryListTopBar(
                onTextChange = viewModel::searchData,
                options = filterList,
                onFilterSelect = viewModel::setSection,
                selectedFilterText =currentFilterText
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding()))

        ) {

            when (uiState) {
                is Resource.Loading -> {LoadingScreen()}
                is Resource.Error -> {ErrorScreen(uiState.message)}
                is Resource.Success -> {
                    if(uiState.data.isNullOrEmpty())
                    {
                        ErrorScreen(ErrorMessage.N0_DATA_FOUND.message)
                    }else{
                        StoryListScreenData(
                            list = uiState.data!!,
                            onItemSelect = {},
                        )
                    }
                }
            }
        }
    }



}

@Composable
fun StoryListScreenData(
    onItemSelect: ()-> Unit,
    list: List<UIArticle>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.inner_padding))
    ){
        items(list) { article ->
            StoryListItem(
                uiArticle = article,
                onItemSelect = onItemSelect
            )
        }
    }
}