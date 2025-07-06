package com.example.readease.data.dao

import androidx.room.*
import com.example.readease.data.model.Books

@Dao
interface BooksDao {

    @Insert
    suspend fun insert(book: Books)

    @Update
    suspend fun update(book: Books)

    @Delete
    suspend fun delete(book: Books)

    @Query("SELECT * FROM books ORDER BY id DESC")
    suspend fun getAll(): List<Books>

    @Query("SELECT * FROM books WHERE title LIKE :title || '%' LIMIT 10")
    suspend fun searchByTitle(title: String): List<Books>

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBookCount(): Int

}