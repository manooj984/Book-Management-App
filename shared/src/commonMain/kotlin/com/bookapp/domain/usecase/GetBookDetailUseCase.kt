package com.bookapp.domain.usecase

import com.bookapp.domain.models.Book
import com.bookapp.domain.repository.BookRepository

class GetBookDetailUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(id: Long): Book? {
        return repository.getBookById(id)
    }
}
