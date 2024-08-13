# New York Times Top Stories App

## Overview

This Android app displays a list of New York Times Top Stories, featuring both a master view and a detailed view of each story. The app allows users to search and filter stories, view them in either list or card format, and read full articles in a WebView. 

The app implements an offline-first approach using Room Database for local storage, ensuring that users can still access previously viewed stories even without an internet connection.

## Features

- **Search**: Find news by title or author name.
- **Filtering**: Filter news stories by section.
- **View Modes**: Switch between list and card views.
- **Detail View**: Access detailed information about each story, including title, author, and a preview of the full article.
- **Read More**: Open and read the full article in a WebView.
- **Shared Preferences**: Save and retrieve the last viewed article.
- **Offline Access**: Store and retrieve news stories locally using Room Database.

## Technical Requirements

The app has been developed with the following technical requirements:

1. **Android App Implementation**: The app is implemented using Jetpack Compose for UI, Kotlin for programming, and Retrofit for API calls.
2. **Search and Filtering**: Users can search by news title or author name and filter stories by section.
3. **Responsive Design**: The app works in both portrait and landscape modes and scales according to screen size.
4. **UI Dynamic Scaling**: The user interface adapts dynamically to various screen sizes.
5. **Context Management**: Ensures that context is not leaked outside the view.
6. **MVVM Architecture**: The app uses the Model-View-ViewModel (MVVM) architecture pattern.
7. **ViewModel Unit Testing**: ViewModel classes have unit test coverage to ensure functionality.
8. **Shared Preferences Util Class**: An easy-access utility class is implemented for managing shared preferences.
9. **Room Database**: Uses Room Database to implement offline-first storage and ensure data persistence.
10. **Kotlin**: The entire codebase is written in Kotlin.
11. **Meaningful README**: This README file provides a clear and comprehensive description of the project.

## Architecture

The app follows the **MVVM (Model-View-ViewModel)** architecture pattern with an additional **Use Case** layer to manage UI-related business logic:

- **Model**: Handles data retrieval and storage operations. It includes data classes, a repository for interacting with remote and local data sources (using Retrofit for API calls and Room Database for offline storage), and data entities.

- **Use Case Layer**: This layer contains use cases that encapsulate the business logic related to user interactions and data processing. It acts as a bridge between the ViewModel and the Model, ensuring that complex business logic is separated from the ViewModel. Each use case performs a specific action or set of actions, such as fetching top stories or filtering news articles.

- **ViewModel**: Manages UI-related data and interacts with the use case layer to execute business logic. It updates the UI state based on the data received from the use cases. The ViewModel is responsible for preparing data for the View and reacting to user interactions.

- **View**: The user interface is built using Jetpack Compose. It renders data and handles user interactions. Composables are used to create responsive and dynamic UI elements, and they observe the ViewModel to reflect changes in the UI.

The separation of concerns into Model, Use Case, ViewModel, and View layers helps in maintaining a clean architecture, making the codebase more modular, testable, and scalable.

## Libraries Used

The following libraries and frameworks are used in this app:

- **Jetpack Compose**: For building modern and dynamic user interfaces in a declarative way. It simplifies UI development with composable functions.

- **Kotlin**: The programming language used for app development. Kotlin provides modern language features and interoperability with Java.

- **Retrofit**: A type-safe HTTP client for Android and Java. It is used for network operations and making API calls to the New York Times API.

- **Coil**: An image loading library for Android that supports image loading, caching, and transformations.

- **Room Database**: An abstraction layer over SQLite that provides a clean API for database access and enables offline storage of news stories.

- **WebView**: For displaying web content within the app, allowing users to view full articles from the New York Times.

## Bonus Features

- **Clean UI**: Designed with a minimalistic approach for a better user experience. The app layout is simple and easy to navigate, reducing visual clutter.

- **Error Handling**: Includes robust error handling for various scenarios:
  - No internet connection: Displays a user-friendly message when the network is unavailable.
  - API Request Errors: Handles errors from API calls gracefully and informs the user.
  - Empty Data States: Provides feedback when there is no data to show.

- **State Management**: Effectively handles orientation changes and maintains state to ensure a seamless user experience. The app preserves the user's place in the list and other UI states during configuration changes.

- **Offline Access**: Implements an offline-first approach using Room Database. This ensures that users can still access previously viewed stories even when there is no internet connection.

