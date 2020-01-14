package com.hytexts.readingposition

import android.text.format.DateUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.room.withTransaction
import com.hytexts.readingposition.epub.EpubReadingDataEntity
import com.hytexts.readingposition.epub.EpubReadingDataViewModel
import com.hytexts.readingposition.pdf.PdfReadingDataEntity
import com.hytexts.readingposition.pdf.PdfReadingDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ReadingPositionHandler {

    companion object {
        private lateinit var epubReadingDataVm: EpubReadingDataViewModel
        private lateinit var pdfReadingDataVm: PdfReadingDataViewModel

        private fun initEpubVm(activity: AppCompatActivity) {
            if (!this::epubReadingDataVm.isInitialized) {
                DatabaseInjection
                    .getEpubReadingDataViewModelFactory(activity)
                    .apply {
                        epubReadingDataVm = ViewModelProviders
                            .of(activity, this)
                            .get(EpubReadingDataViewModel::class.java)
                    }
            }
        }

        private fun initPdfVm(activity: AppCompatActivity) {
            if (!this::pdfReadingDataVm.isInitialized) {
                DatabaseInjection
                    .getPdfReadingDataViewModelFactory(activity)
                    .apply {
                        pdfReadingDataVm = ViewModelProviders
                            .of(activity, this)
                            .get(PdfReadingDataViewModel::class.java)
                    }
            }
        }

        @JvmStatic
        fun addEpubReadingData(
            activity: AppCompatActivity,
            bookId: String,
            fontName: String,
            chapterIndex: Int,
            chapterPosition: Float
        ) {
            initEpubVm(activity)
            EpubReadingDataEntity(
                bookId,
                fontName,
                chapterIndex,
                chapterPosition,
                System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS
            ).apply {
                CoroutineScope(Dispatchers.IO).launch {
                    epubReadingDataVm
                        .insertEpubReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

        @JvmStatic
        fun addPdfReadingData(
            activity: AppCompatActivity,
            bookId: String,
            bookPosition: Float
        ) {
            initPdfVm(activity)
            PdfReadingDataEntity(
                bookId,
                bookPosition,
                System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS
            ).apply {
                CoroutineScope(Dispatchers.IO).launch {
                    pdfReadingDataVm
                        .insertPdfReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

        @JvmStatic
        fun getAllEpubReadingData(activity: AppCompatActivity): List<EpubReadingDataEntity>? {
            initEpubVm(activity)
            return runBlocking {
                epubReadingDataVm
                    .findAllEpubReadingDataEntityAsync()
                    .await()
            }
        }

        @JvmStatic
        fun getAllPdfReadingData(activity: AppCompatActivity): List<PdfReadingDataEntity>? {
            initPdfVm(activity)
            return runBlocking {
                pdfReadingDataVm
                    .findAllPdfReadingDataEntityAsync()
                    .await()
            }
        }

        @JvmStatic
        fun getEpubReadingData(
            activity: AppCompatActivity,
            bookId: String
        ): EpubReadingDataEntity? {
            initEpubVm(activity)
            return runBlocking {
                epubReadingDataVm
                    .findEpubReadingDataEntityAsync(bookId)
                    .await()
            }
        }

        @JvmStatic
        fun getPdfReadingData(
            activity: AppCompatActivity,
            bookId: String
        ): PdfReadingDataEntity? {
            initEpubVm(activity)
            return runBlocking {
                pdfReadingDataVm
                    .findPdfReadingDataEntityAsync(bookId)
                    .await()
            }
        }

        @JvmStatic
        fun updateEpubReadingData(
            activity: AppCompatActivity,
            bookId: String,
            fontName: String,
            chapterIndex: Int,
            chapterPosition: Float
        ) {
            initEpubVm(activity)
            getEpubReadingData(activity, bookId)?.apply {
                this.fontName = fontName
                this.chapterIndex = chapterIndex
                this.chapterPosition = chapterPosition
                this.timestamp = System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS

                CoroutineScope(Dispatchers.IO).launch {
                    epubReadingDataVm
                        .updateEpubReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

        @JvmStatic
        fun updatePdfReadingData(
            activity: AppCompatActivity,
            bookId: String,
            bookPosition: Float
        ) {
            initPdfVm(activity)
            getPdfReadingData(activity, bookId)?.apply {
                this.bookPosition = bookPosition
                this.timestamp = System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS

                CoroutineScope(Dispatchers.IO).launch {
                    pdfReadingDataVm
                        .updatePdfReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

        @JvmStatic
        fun deleteEpubReadingData(
            activity: AppCompatActivity,
            bookId: String
        ) {
            initEpubVm(activity)
            CoroutineScope(Dispatchers.IO).launch {
                epubReadingDataVm
                    .deleteEpubReadingDataEntityAsync(bookId)
                    .await()
            }
        }

        @JvmStatic
        fun deletePdfReadingData(
            activity: AppCompatActivity,
            bookId: String
        ) {
            initPdfVm(activity)
            CoroutineScope(Dispatchers.IO).launch {
                pdfReadingDataVm
                    .deletePdfReadingDataEntityAsync(bookId)
                    .await()
            }
        }

        @JvmStatic
        fun clearEpubDatabase(activity: AppCompatActivity) {
            initEpubVm(activity)
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getInstance(activity).withTransaction {
                    epubReadingDataVm.clear()
                }
            }
        }

        @JvmStatic
        fun clearPdfDatabase(activity: AppCompatActivity) {
            initPdfVm(activity)
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getInstance(activity).withTransaction {
                    pdfReadingDataVm.clear()
                }
            }
        }

    }

}
