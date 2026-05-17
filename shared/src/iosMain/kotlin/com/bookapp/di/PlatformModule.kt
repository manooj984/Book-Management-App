package com.bookapp.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.bookapp.data.local.BookDb
import com.bookapp.data.preferences.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun platformModule(): Module = module {
    single {
        val driver = NativeSqliteDriver(BookDb.Schema, "book.db")
        BookDb(driver)
    }
    single {
        createDataStore {
            val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null,
            )
            requireNotNull(documentDirectory).path + "/book_preferences.preferences_pb"
        }
    }
}
