package com.example.readease.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "collection",
    foreignKeys = [
        ForeignKey(
            entity = Books::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MyCollection(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val bookId: Int
) : Parcelable
