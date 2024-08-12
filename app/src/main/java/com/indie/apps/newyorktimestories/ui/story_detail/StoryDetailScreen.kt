package com.indie.apps.newyorktimestories.ui.story_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.indie.apps.newyorktimestories.R
import com.indie.apps.newyorktimestories.data.PreferencesManager
import com.indie.apps.newyorktimestories.ui.common.ErrorScreen
import com.indie.apps.newyorktimestories.ui.common.LoadingScreen
import com.indie.apps.newyorktimestories.ui.story_detail.component.StoryDetailData
import com.indie.apps.newyorktimestories.ui.story_detail.component.StoryDetailTopBar
import com.indie.apps.newyorktimestories.util.Constant
import com.indie.apps.newyorktimestories.util.ErrorMessage
import com.indie.apps.newyorktimestories.util.Resource

@Composable
fun StoryDetailScreen(
    onSeeMoreClick: (Long) -> Unit,
    onNavigationBack: () -> Unit,
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var viewModel: StoryDetailViewModel = viewModel(
        factory = StoryDetailViewModelFactory()
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            StoryDetailTopBar(
                onNavigationBack = onNavigationBack
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(paddingValues = PaddingValues(top = innerPadding.calculateTopPadding()))
                .padding(dimensionResource(id = R.dimen.inner_padding))

        ) {
            //
            when (uiState) {
                is Resource.Loading -> {
                    LoadingScreen()
                }

                is Resource.Error -> {
                    ErrorScreen(uiState.message)
                }

                is Resource.Success -> {
                    if (uiState.data != null) {

                        preferencesManager.saveData(Constant.PREF_ARTICLE_TITLE, uiState.data!!.title)

                        StoryDetailData(
                            uiArticle = uiState.data!!,
                            onSeeMoreClick = onSeeMoreClick)
                    } else {
                        ErrorScreen(ErrorMessage.N0_DATA_FOUND.message)
                    }

                }
            }
        }
    }
}
