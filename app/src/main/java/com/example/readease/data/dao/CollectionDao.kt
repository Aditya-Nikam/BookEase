package com.example.readease.data.dao

import androidx.room.*
import com.example.readease.data.model.MyCollection

@Dao
interface CollectionDao {

    @Insert
    suspend fun addToCollection(item: MyCollection)

    @Delete
    suspend fun removeFromCollection(item: MyCollection)

    @Query("SELECT * FROM collection")
    suspend fun getAll(): List<MyCollection>

    @Query("SELECT * FROM collection WHERE bookId = :bookId LIMIT 1")
    suspend fun findByBookId(bookId: Int): MyCollection?
}