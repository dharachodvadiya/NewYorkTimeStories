package com.indie.apps.newyorktimestories.ui.story_list

import com.indie.apps.newyorktimestories.ui.model.UIArticle
import com.indie.apps.newyorktimestories.usecase.GetStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.usecase.SearchStoriesFromSectionUseCase
import com.indie.apps.newyorktimestories.util.Resource
import com.indie.apps.newyorktimestories.utils.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.bytebuddy.description.type.TypeDescription.Generic.Visitor.Assigner.Dispatcher
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class StoryListViewModelTest {

    private val getDataUseCase: GetStoriesFromSectionUseCase = Mockito.mock(GetStoriesFromSectionUseCase::class.java)
    private val searchDataUseCase: SearchStoriesFromSectionUseCase = Mockito.mock(SearchStoriesFromSectionUseCase::class.java)

    private lateinit var storyListViewModel: StoryListViewModel

    private val testDispatcher = StandardTestDispatcher()
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        storyListViewModel = StoryListViewModel(getDataUseCase,searchDataUseCase)
    }

    @Test
    fun `get data with two fake data`()
    {
        runBlocking {
            val flow = flow {
                emit(Resource.Loading())
                delay(10)

                val uiArticleList = listOf(
                    UIArticle(1,"Title 1", "author 1", emptyList(), "sample data 1", ""),
                    UIArticle(2,"Title 2", "author 2", emptyList(), "sample data 2", "")
                )
                emit(Resource.Success(uiArticleList))
            }
            Mockito.`when`(getDataUseCase.loadData(anyString())).thenReturn(flow)
            Mockito.`when`(searchDataUseCase.loadData(anyString(), anyString())).thenReturn(flow)

            runTest {
                val state = storyListViewModel.uiState
                val testCollector = state.test(this, numberOfEmit = 2)
                storyListViewModel.getDataWithSection()


                testDispatcher.scheduler.advanceUntilIdle()
                val result = testCollector.getData()
                assert(result.size == 2)
            }

        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}