package com.example.readease.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readease.data.model.Books
import com.example.readease.repository.BooksRepository
import kotlinx.coroutines.launch

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Books>>()
    val books: LiveData<List<Books>> get() = _books

    fun loadBooks() = viewModelScope.launch {
        _books.value = repository.getAll()
    }

    fun searchBooks(title: String) = viewModelScope.launch {
        _books.value = repository.searchByTitle(title)
    }

    fun insert(book: Books) = viewModelScope.launch {
        repository.insert(book)
        loadBooks()
    }

    fun update(book: Books) = viewModelScope.launch {
        repository.update(book)
        loadBooks()
    }

    fun delete(book: Books) = viewModelScope.launch {
        repository.delete(book)
        loadBooks()
    }
}