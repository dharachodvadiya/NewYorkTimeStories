package com.allvideo.status.free.ui.story_detail

import androidx.lifecycle.SavedStateHandle
import com.allvideo.status.free.downloade.ui.model.UIArticle
import com.allvideo.status.free.downloade.ui.story_detail.StoryDetailViewModel
import com.allvideo.status.free.downloade.usecase.GetStoriesFromIdUseCase
import com.allvideo.status.free.downloade.util.Constant
import com.allvideo.status.free.downloade.util.Resource
import com.allvideo.status.free.utils.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

class StoryDetailViewModelTest {
    private val getStoriesFromIdUseCase: GetStoriesFromIdUseCase = Mockito.mock(
        GetStoriesFromIdUseCase::class.java)

    private lateinit var storyDetailsViewModel: StoryDetailViewModel

    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        val savedStateHandle = SavedStateHandle().apply {
            set(Constant.PARAM_ARTICLE_ID, "5")
        }
        storyDetailsViewModel = StoryDetailViewModel(getStoriesFromIdUseCase,savedStateHandle)
    }

    @Test
    fun `get fake data from id`(){

        val flow = flow {
            emit(Resource.Loading())
            //delay(10)

            val uiArticle = UIArticle(5,"Title 5", "author 5", emptyList(), "sample data 5", "")
            emit(Resource.Success(uiArticle))
        }

        Mockito.`when`(getStoriesFromIdUseCase.loadData(any())).thenReturn(flow)

        runTest {
            val state = storyDetailsViewModel.uiState
            val testCollector = state.test(this, numberOfEmit = 2)
            storyDetailsViewModel.getArticle(5)


            testDispatcher.scheduler.advanceUntilIdle()
            val result = testCollector.getData()
            assert(result.size == 2)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}