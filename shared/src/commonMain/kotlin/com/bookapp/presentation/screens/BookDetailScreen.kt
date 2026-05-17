package com.bookapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bookapp.presentation.viewmodel.BookDetailViewModel

@Composable
fun BookDetailScreen(
    id: Long,
    viewModel: BookDetailViewModel,
    onBack: () -> Unit
) {
    val book by viewModel.book.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(id) {
        viewModel.loadBook(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Loading...") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                book?.let {
                    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
                        Text(text = "Title: ${it.title}", style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Pages: ${it.pageCount}", style = MaterialTheme.typography.subtitle1)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Published: ${it.publishDate}", style = MaterialTheme.typography.subtitle2)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Description:", style = MaterialTheme.typography.h6)
                        Text(text = it.description, style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}
