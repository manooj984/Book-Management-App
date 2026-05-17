package com.bookapp.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: Long,
    val title: String,
    val description: String,
    val pageCount: Int,
    val excerpt: String,
    val publishDate: String
)
