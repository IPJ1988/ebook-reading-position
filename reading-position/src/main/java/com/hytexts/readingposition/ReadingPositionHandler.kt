package com.hytexts.readingposition

import android.text.format.DateUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.room.withTransaction
import com.hytexts.readingposition.epub.EpubReadingDataEntity
import com.hytexts.readingposition.epub.EpubReadingDataViewModel
import com.hytexts.readingposition.pdf.PdfReadingDataEntity
import com.hytexts.readingposition.pdf.PdfReadingDataViewModel
import kotlinx.coroutines.*

class ReadingPositionHandler(private val activity: AppCompatActivity) {

    private lateinit var epubReadingDataVm: EpubReadingDataViewModel
    private lateinit var pdfReadingDataVm: PdfReadingDataViewModel

    private val currentTimeSecond: Long
        get() = System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS

    private fun initEpubVm() {
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

    private fun initPdfVm() {
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

    suspend fun addEpubReadingData(
        bookId: String,
        fontName: String,
        chapter: Int,
        progress: Float
    ): Boolean = EpubReadingDataEntity(bookId, fontName, chapter, progress, currentTimeSecond)
        .let {
            initEpubVm()
            epubReadingDataVm.insertEpubReadingDataEntityAsync(it).await()
        }

    suspend fun addPdfReadingData(
        bookId: String,
        bookPosition: Float
    ): Boolean = PdfReadingDataEntity(bookId, bookPosition, currentTimeSecond)
        .let {
            initPdfVm()
            pdfReadingDataVm.insertPdfReadingDataEntityAsync(it).await()
        }

    suspend fun getAllEpubReadingData(): List<EpubReadingDataEntity>? {
        initEpubVm()
        return epubReadingDataVm.findAllEpubReadingDataEntityAsync().await()
    }

    suspend fun getAllPdfReadingData(): List<PdfReadingDataEntity>? {
        initPdfVm()
        return pdfReadingDataVm.findAllPdfReadingDataEntityAsync().await()
    }

    suspend fun getEpubReadingData(bookId: String): EpubReadingDataEntity? {
        initEpubVm()
        return epubReadingDataVm.findEpubReadingDataEntityAsync(bookId).await()
    }

    suspend fun getPdfReadingData(bookId: String): PdfReadingDataEntity? {
        initPdfVm()
        return pdfReadingDataVm.findPdfReadingDataEntityAsync(bookId).await()
    }

    suspend fun updateEpubReadingData(
        bookId: String,
        fontName: String,
        chapter: Int,
        progress: Float
    ): Boolean {
        return getEpubReadingData(bookId)?.let {
            it.fontName = fontName
            it.chapterIndex = chapter
            it.chapterPosition = progress
            it.timestamp = currentTimeSecond

            initEpubVm()
            epubReadingDataVm.updateEpubReadingDataEntityAsync(it).await()
        } ?: false
    }

    suspend fun updatePdfReadingData(bookId: String, bookPosition: Float): Boolean {
        return getPdfReadingData(bookId)?.let {
            it.bookPosition = bookPosition
            it.timestamp = currentTimeSecond

            initPdfVm()
            pdfReadingDataVm.updatePdfReadingDataEntityAsync(it).await()
        } ?: false
    }

    suspend fun deleteEpubReadingData(bookId: String): Boolean {
        initEpubVm()
        return epubReadingDataVm.deleteEpubReadingDataEntityAsync(bookId).await()
    }

    suspend fun deletePdfReadingData(bookId: String): Boolean {
        initPdfVm()
        return pdfReadingDataVm.deletePdfReadingDataEntityAsync(bookId).await()
    }

    fun clearEpubDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(activity).withTransaction {
                initEpubVm()
                epubReadingDataVm.clear()
            }
        }
    }

    fun clearPdfDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(activity).withTransaction {
                initPdfVm()
                pdfReadingDataVm.clear()
            }
        }
    }

}
