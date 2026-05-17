package com.bookapp.di

import com.bookapp.data.preferences.AppPreferences
import com.bookapp.data.remote.BookApiClient
import com.bookapp.data.remote.createHttpClient
import com.bookapp.data.repository.BookRepositoryImpl
import com.bookapp.domain.repository.BookRepository
import com.bookapp.domain.usecase.AddBookUseCase
import com.bookapp.domain.usecase.DeleteBookUseCase
import com.bookapp.domain.usecase.GetBookDetailUseCase
import com.bookapp.domain.usecase.GetBooksUseCase
import com.bookapp.presentation.viewmodel.AddBookViewModel
import com.bookapp.presentation.viewmodel.BookDetailViewModel
import com.bookapp.presentation.viewmodel.BookListViewModel
import com.bookapp.presentation.viewmodel.MainViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(appModule(), platformModule())
    }

fun initKoin() = initKoin {}

fun appModule() = module {
    // Network
    single { createHttpClient() }
    single { BookApiClient(get()) }

    // Repository
    single<BookRepository> { BookRepositoryImpl(get(), get()) }
    
    // Preferences
    single { AppPreferences(get()) }

    // UseCases
    factory { GetBooksUseCase(get()) }
    factory { AddBookUseCase(get()) }
    factory { DeleteBookUseCase(get()) }
    factory { GetBookDetailUseCase(get()) }

    // ViewModels
    factory { BookListViewModel(get(), get(), get()) }
    factory { BookDetailViewModel(get()) }
    factory { AddBookViewModel(get()) }
    factory { MainViewModel(get()) }
}
