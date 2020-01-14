package com.hytexts.readingposition

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hytexts.readingposition.epub.EpubReadingDataDao
import com.hytexts.readingposition.epub.EpubReadingDataEntity
import com.hytexts.readingposition.pdf.PdfReadingDataDao
import com.hytexts.readingposition.pdf.PdfReadingDataEntity

@Database(
    entities = [EpubReadingDataEntity::class, PdfReadingDataEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun epubReadingDataDao(): EpubReadingDataDao
    abstract fun pdfReadingDataDao(): PdfReadingDataDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): AppDatabase = Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "reading_data.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

}
