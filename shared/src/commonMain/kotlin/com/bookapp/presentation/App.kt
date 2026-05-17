package com.bookapp.presentation

import androidx.compose.runtime.*
import com.bookapp.presentation.navigation.Screen
import com.bookapp.presentation.screens.AddBookScreen
import com.bookapp.presentation.screens.BookDetailScreen
import com.bookapp.presentation.screens.BookListScreen
import com.bookapp.presentation.screens.SplashScreen
import com.bookapp.presentation.theme.AppTheme
import com.bookapp.presentation.viewmodel.*
import org.koin.compose.koinInject

@Composable
fun App() {
    val mainViewModel = koinInject<MainViewModel>()
    val isDarkMode by mainViewModel.isDarkMode.collectAsState()

    var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

    AppTheme(darkTheme = isDarkMode) {
        when (val screen = currentScreen) {
            is Screen.Splash -> {
                SplashScreen(onSplashFinished = { currentScreen = Screen.BookList })
            }
            is Screen.BookList -> {
                val viewModel = koinInject<BookListViewModel>()
                BookListScreen(
                    viewModel = viewModel,
                    onBookClick = { id -> currentScreen = Screen.BookDetail(id) },
                    onAddClick = { currentScreen = Screen.AddBook }
                )
            }
            is Screen.BookDetail -> {
                val viewModel = koinInject<BookDetailViewModel>()
                BookDetailScreen(
                    id = screen.id,
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.BookList }
                )
            }
            is Screen.AddBook -> {
                val viewModel = koinInject<AddBookViewModel>()
                AddBookScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.BookList }
                )
            }
        }
    }
}
