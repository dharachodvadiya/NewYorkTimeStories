package com.allvideo.status.free.downloade.ui.story_list

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringArrayResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.allvideo.status.free.downloade.R
import com.allvideo.status.free.downloade.data.PreferencesManager
import com.allvideo.status.free.downloade.ui.common.ErrorScreen
import com.allvideo.status.free.downloade.ui.common.LoadingScreen
import com.allvideo.status.free.downloade.ui.model.UIArticle
import com.allvideo.status.free.downloade.ui.story_list.component.RecentlyViewedItem
import com.allvideo.status.free.downloade.ui.story_list.component.StoryCardListItem
import com.allvideo.status.free.downloade.ui.story_list.component.StoryListItem
import com.allvideo.status.free.downloade.ui.story_list.component.StoryListTopBar
import com.allvideo.status.free.downloade.util.Constant
import com.allvideo.status.free.downloade.util.ErrorMessage
import com.allvideo.status.free.downloade.util.Resource

@Composable
fun StoryListScreen(
    onItemClick : (Long) -> Unit,
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val prefTitle by remember { mutableStateOf(preferencesManager.getData(Constant.PREF_ARTICLE_TITLE, "")) }


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
                    LoadingScreen(modifier = Modifier.weight(1f))
                }

                is Resource.Error -> {
                    ErrorScreen(uiState.message, modifier = Modifier.weight(1f))
                }

                is Resource.Success -> {
                    if (uiState.data.isNullOrEmpty()) {
                        ErrorScreen(ErrorMessage.N0_DATA_FOUND.message)
                    } else {
                        if (enabledList) {
                            StoryListScreenData(
                                list = uiState.data!!,
                                onItemSelect = onItemClick,
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            StoryCardListScreenData(
                                list = uiState.data!!,
                                onItemSelect = onItemClick,
                                modifier = Modifier.weight(1f)
                            )
                        }

                    }
                }
            }
            if(prefTitle.isNotEmpty())
            {
                RecentlyViewedItem(
                    title = prefTitle
                )
            }

        }
    }


}

@Composable
fun StoryListScreenData(
    onItemSelect: (Long) -> Unit,
    list: List<UIArticle>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.inner_padding))
    ) {
        items(
            items = list,
            key = {item -> item.id}) { article ->
            StoryListItem(
                uiArticle = article,
                onItemSelect = {onItemSelect(article.id)}
            )
        }
    }
}

@Composable
fun StoryCardListScreenData(
    onItemSelect: (Long) -> Unit,
    list: List<UIArticle>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.inner_padding)),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.inner_padding))
    ) {
        items(
            count = list.size,
            key = {index -> list[index].id}) { index ->
            StoryCardListItem(
                uiArticle = list[index],
                onItemSelect = {
                    onItemSelect(list[index].id)
                })
        }
    }
}