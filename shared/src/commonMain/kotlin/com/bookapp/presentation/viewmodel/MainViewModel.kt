package com.bookapp.presentation.viewmodel

import com.bookapp.data.preferences.AppPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val appPreferences: AppPreferences
) : ViewModel() {

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        viewModelScope.launch {
            appPreferences.isDarkMode.collect {
                _isDarkMode.value = it
            }
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            appPreferences.setDarkMode(!_isDarkMode.value)
        }
    }
}
