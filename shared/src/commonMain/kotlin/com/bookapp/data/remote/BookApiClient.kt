package com.bookapp.data.remote

import com.bookapp.data.remote.models.BookDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BookApiClient(private val httpClient: HttpClient) {
    
    companion object {
        const val BASE_URL = "https://fakerestapi.azurewebsites.net/api/v1/Books"
    }

    suspend fun getBooks(): List<BookDto> {
        return httpClient.get(BASE_URL).body()
    }

    suspend fun getBook(id: Long): BookDto {
        return httpClient.get("$BASE_URL/$id").body()
    }

    suspend fun addBook(book: BookDto) {
        httpClient.post(BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(book)
        }
    }

    suspend fun deleteBook(id: Long) {
        httpClient.delete("$BASE_URL/$id")
    }
}

fun createHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("Ktor: $message")
            }
        }
        level = LogLevel.ALL
    }
}
