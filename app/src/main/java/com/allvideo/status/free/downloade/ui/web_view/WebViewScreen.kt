package com.allvideo.status.free.downloade.ui.web_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.allvideo.status.free.downloade.R
import com.allvideo.status.free.downloade.ui.common.ErrorScreen
import com.allvideo.status.free.downloade.ui.common.LoadingScreen
import com.allvideo.status.free.downloade.ui.story_detail.StoryDetailViewModel
import com.allvideo.status.free.downloade.ui.story_detail.StoryDetailViewModelFactory
import com.allvideo.status.free.downloade.ui.web_view.component.WebView
import com.allvideo.status.free.downloade.util.ErrorMessage
import com.allvideo.status.free.downloade.util.Resource

@Composable
fun WebViewScreen() {

    var viewModel: StoryDetailViewModel = viewModel(
        factory = StoryDetailViewModelFactory()
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold() { innerPadding ->

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
                        WebView(uiState.data!!.url)
                    } else {
                        ErrorScreen(ErrorMessage.N0_DATA_FOUND.message)
                    }

                }
            }
        }
    }
}
