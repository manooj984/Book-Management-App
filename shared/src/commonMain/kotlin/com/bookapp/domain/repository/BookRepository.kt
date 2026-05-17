package com.bookapp.domain.repository

import com.bookapp.domain.models.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(): Flow<List<Book>>
    suspend fun fetchBooksFromRemote()
    suspend fun getBookById(id: Long): Book?
    suspend fun addBook(book: Book)
    suspend fun deleteBook(id: Long)
}
