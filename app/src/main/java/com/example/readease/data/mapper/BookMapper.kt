package com.example.readease.data.mapper

import com.example.readease.data.api.BookItem
import com.example.readease.data.model.Books

fun BookItem.toRoomEntity(subject: String): Books {
    return Books(
        title = volumeInfo.title,
        author = volumeInfo.authors?.joinToString(", ") ?: "Unknown",
        subject = subject,
        category = volumeInfo.categories?.firstOrNull() ?: "General",
        description = volumeInfo.description ?: "No description available",
        thumbnail = volumeInfo.imageLinks?.thumbnail ?: ""
    )
}