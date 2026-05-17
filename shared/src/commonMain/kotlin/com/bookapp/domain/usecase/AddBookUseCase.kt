package com.bookapp.domain.usecase

import com.bookapp.domain.models.Book
import com.bookapp.domain.repository.BookRepository

class AddBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(book: Book) {
        repository.addBook(book)
    }
}
