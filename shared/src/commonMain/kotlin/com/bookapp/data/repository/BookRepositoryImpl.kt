package com.bookapp.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bookapp.data.local.BookDb
import com.bookapp.data.mapper.toDomainBook
import com.bookapp.data.mapper.toDto
import com.bookapp.data.mapper.toEntity
import com.bookapp.data.remote.BookApiClient
import com.bookapp.domain.models.Book
import com.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val apiClient: BookApiClient,
    private val db: BookDb
) : BookRepository {

    private val queries = db.bookDbQueries

    override fun getBooks(): Flow<List<Book>> {
        return queries.getAllBooks()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomainBook() } }
    }

    override suspend fun fetchBooksFromRemote() {
        try {
            val remoteBooks = apiClient.getBooks()
            queries.transaction {
                remoteBooks.forEach { dto ->
                    queries.insertBook(
                        id = dto.id,
                        title = dto.title,
                        description = dto.description,
                        pageCount = dto.pageCount.toLong(),
                        excerpt = dto.excerpt,
                        publishDate = dto.publishDate
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error, optionally rethrow or log
        }
    }

    override suspend fun getBookById(id: Long): Book? {
        return queries.getBookById(id).executeAsOneOrNull()?.toDomainBook()
    }

    override suspend fun addBook(book: Book) {
        try {
            val dto = book.toDto()
            // Add remotely
            apiClient.addBook(dto)
            // Add locally
            queries.insertBook(
                id = book.id,
                title = book.title,
                description = book.description,
                pageCount = book.pageCount.toLong(),
                excerpt = book.excerpt,
                publishDate = book.publishDate
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun deleteBook(id: Long) {
        try {
            // Delete remotely
            apiClient.deleteBook(id)
            // Delete locally
            queries.deleteBookById(id)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
