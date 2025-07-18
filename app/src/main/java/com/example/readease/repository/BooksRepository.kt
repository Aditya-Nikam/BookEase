package com.example.readease.repository

import com.example.readease.data.api.BookItem
import com.example.readease.data.dao.BooksDao
import com.example.readease.data.model.Books

class BooksRepository(private val booksDao: BooksDao) {

    suspend fun insert(book: Books) {
        booksDao.insert(book)
    }

    suspend fun update(book: Books) {
        booksDao.update(book)
    }

    suspend fun delete(book: Books) {
        booksDao.delete(book)
    }

    suspend fun getAll(): List<Books> {
        return booksDao.getAll()
    }

    suspend fun searchByTitle(title: String): List<Books> {
        return booksDao.searchByTitle(title)
    }
    suspend fun isBooksTableEmpty(): Boolean {
        return booksDao.getBookCount() == 0
    }

    suspend fun insertBooks(books: List<Books>) {
        books.forEach { booksDao.insert(it) }
    }
}