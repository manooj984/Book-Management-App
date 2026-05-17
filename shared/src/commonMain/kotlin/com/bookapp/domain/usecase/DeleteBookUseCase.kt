package com.bookapp.domain.usecase

import com.bookapp.domain.repository.BookRepository

class DeleteBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deleteBook(id)
    }
}
