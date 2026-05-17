package com.bookapp.presentation.viewmodel

import com.bookapp.domain.models.Book
import com.bookapp.domain.usecase.GetBookDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val getBookDetailUseCase: GetBookDetailUseCase
) : ViewModel() {

    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadBook(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _book.value = getBookDetailUseCase(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
