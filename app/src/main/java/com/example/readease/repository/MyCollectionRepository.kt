package com.example.readease.repository

import com.example.readease.data.dao.CollectionDao
import com.example.readease.data.model.MyCollection

class MyCollectionRepository(private val collectionDao: CollectionDao) {

    suspend fun addToCollection(item: MyCollection) {
        collectionDao.addToCollection(item)
    }

    suspend fun removeFromCollection(item: MyCollection) {
        collectionDao.removeFromCollection(item)
    }

    suspend fun getAll(): List<MyCollection> {
        return collectionDao.getAll()
    }

    suspend fun findByBookId(bookId: Int): MyCollection? {
        return collectionDao.findByBookId(bookId)
    }
}