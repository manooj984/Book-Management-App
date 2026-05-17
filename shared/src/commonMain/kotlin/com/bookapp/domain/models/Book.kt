package com.bookapp.domain.models

data class Book(
    val id: Long,
    val title: String,
    val description: String,
    val pageCount: Int,
    val excerpt: String,
    val publishDate: String
)
