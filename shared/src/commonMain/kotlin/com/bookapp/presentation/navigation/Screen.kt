package com.bookapp.presentation.navigation

sealed class Screen {
    object Splash : Screen()
    object BookList : Screen()
    data class BookDetail(val id: Long) : Screen()
    object AddBook : Screen()
}
