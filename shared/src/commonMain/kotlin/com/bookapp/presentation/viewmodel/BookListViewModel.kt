package com.bookapp.presentation.viewmodel

import com.bookapp.domain.models.Book
import com.bookapp.domain.repository.BookRepository
import com.bookapp.domain.usecase.GetBooksUseCase
import com.bookapp.domain.usecase.DeleteBookUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookListViewModel(
    private val getBooksUseCase: GetBooksUseCase,
    private val deleteBookUseCase: DeleteBookUseCase,
    private val repository: BookRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadBooks()
        fetchFromRemote()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            getBooksUseCase()
                .catch { e -> e.printStackTrace() }
                .collect { 
                    _books.value = it
                }
        }
    }

    fun fetchFromRemote() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.fetchBooksFromRemote()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteBook(id: Long) {
        viewModelScope.launch {
            try {
                deleteBookUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
