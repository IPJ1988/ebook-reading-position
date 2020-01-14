package com.hytexts.readingposition.epub

import android.text.format.DateUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EpubReadingDataEntity")
data class EpubReadingDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "book_id")
    var bookId: String = "",
    @ColumnInfo(name = "font_name")
    var fontName: String = "",
    @ColumnInfo(name = "chapter_index")
    var chapterIndex: Int = 0,
    @ColumnInfo(name = "chapter_position")
    var chapterPosition: Float = 0.0f,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long = System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS
)
