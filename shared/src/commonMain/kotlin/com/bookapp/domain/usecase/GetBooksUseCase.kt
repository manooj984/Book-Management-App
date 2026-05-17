package com.bookapp.domain.usecase

import com.bookapp.domain.models.Book
import com.bookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class GetBooksUseCase(private val repository: BookRepository) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getBooks()
    }
}
