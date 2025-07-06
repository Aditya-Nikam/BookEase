package com.example.readease.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.readease.data.dao.BooksDao
import com.example.readease.data.dao.CollectionDao
import com.example.readease.data.dao.UserDao
import com.example.readease.data.model.Books
import com.example.readease.data.model.MyCollection
import com.example.readease.data.model.User

@Database(
    entities = [User::class, Books::class, MyCollection::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun booksDao(): BooksDao
    abstract fun collectionDao(): CollectionDao
    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "readease_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
