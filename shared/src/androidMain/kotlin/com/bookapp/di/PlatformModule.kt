package com.bookapp.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.bookapp.data.local.BookDb
import com.bookapp.data.preferences.createDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single {
        val driver = AndroidSqliteDriver(BookDb.Schema, androidContext(), "book.db")
        BookDb(driver)
    }
    single {
        createDataStore {
            androidContext().filesDir.resolve("book_preferences.preferences_pb").absolutePath
        }
    }
}
