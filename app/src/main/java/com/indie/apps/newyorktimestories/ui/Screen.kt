package com.indie.apps.newyorktimestories.ui

sealed class Screen(val route: String) {
    data object StoryListScreen: Screen("story_list_screen")
    data object StoryDetailScreen: Screen("story_detail_screen")
    data object WebViewScreen: Screen("web_view_screen")
}