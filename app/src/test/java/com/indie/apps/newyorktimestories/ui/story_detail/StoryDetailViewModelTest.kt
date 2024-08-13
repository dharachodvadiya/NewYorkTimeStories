package com.indie.apps.newyorktimestories.ui.story_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.ui.story_list.StoryListViewModel
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromIdUseCase
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.usecase.SearchStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.util.Constant
import com.indie.apps.newyorktimestories.util.Resource
import com.indie.apps.newyorktimestories.utils.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
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