package com.bookapp.data.mapper

import com.bookapp.data.local.BookEntity
import com.bookapp.data.remote.models.BookDto
import com.bookapp.domain.models.Book

fun BookEntity.toDomainBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        pageCount = pageCount.toInt(),
        excerpt = excerpt,
        publishDate = publishDate
    )
}

fun BookDto.toEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        pageCount = pageCount.toLong(),
        excerpt = excerpt,
        publishDate = publishDate
    )
}

fun Book.toDto(): BookDto {
    return BookDto(
        id = id,
        title = title,
        description = description,
        pageCount = pageCount,
        excerpt = excerpt,
        publishDate = publishDate
    )
}
