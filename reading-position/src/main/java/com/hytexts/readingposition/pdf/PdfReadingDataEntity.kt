package com.hytexts.readingposition.pdf

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PdfReadingDataEntity")
data class PdfReadingDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "book_id")
    var bookId: String = "",
    @ColumnInfo(name = "book_position")
    var bookPosition: Float? = 0.0f,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long? = 0L
)
