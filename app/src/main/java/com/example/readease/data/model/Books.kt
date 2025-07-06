package com.example.readease.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "books")
data class Books(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val subject: String,
    val category: String,
    val description: String,
    val thumbnail: String,
    val collected: Boolean = false
) : Parcelable
