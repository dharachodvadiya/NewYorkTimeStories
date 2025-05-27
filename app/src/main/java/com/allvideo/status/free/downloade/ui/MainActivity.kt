package com.allvideo.status.free.downloade.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.allvideo.status.free.downloade.ui.story_detail.StoryDetailScreen
import com.allvideo.status.free.downloade.ui.story_list.StoryListScreen
import com.allvideo.status.free.downloade.ui.theme.NewYorkTimeStoriesTheme
import com.allvideo.status.free.downloade.ui.web_view.WebViewScreen
import com.allvideo.status.free.downloade.util.Constant

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewYorkTimeStoriesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StoryListScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            route = Screen.StoryListScreen.route
                        ) {
                            StoryListScreen(
                                onItemClick = {
                                    navController.navigate(Screen.StoryDetailScreen.route + "/${it}")
                                }
                            )
                        }
                        composable(
                            route = Screen.StoryDetailScreen.route + "/{${Constant.PARAM_ARTICLE_ID}}",
                        ) {
                            StoryDetailScreen(
                                onNavigationBack = {
                                    navController.navigateUp()
                                },
                                onSeeMoreClick = {
                                    navController.navigate(Screen.WebViewScreen.route + "/${it}")
                                }
                            )
                        }
                        composable(
                            route = Screen.WebViewScreen.route + "/{${Constant.PARAM_ARTICLE_ID}}",
                        ) {
                           WebViewScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewYorkTimeStoriesTheme {
        Greeting("Android")
    }
}