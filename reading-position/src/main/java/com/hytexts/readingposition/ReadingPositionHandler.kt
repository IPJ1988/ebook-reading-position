package com.hytexts.readingposition

import android.text.format.DateUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.hytexts.readingposition.epub.EpubReadingDataEntity
import com.hytexts.readingposition.epub.EpubReadingDataViewModel
import com.hytexts.readingposition.pdf.PdfReadingDataEntity
import com.hytexts.readingposition.pdf.PdfReadingDataViewModel
import kotlinx.coroutines.runBlocking

class ReadingPositionHandler {

    companion object {
        private lateinit var epubReadingDataVm: EpubReadingDataViewModel
        private lateinit var pdfReadingDataVm: PdfReadingDataViewModel

        fun persistEpubReadingData(
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
                runBlocking {
                    epubReadingDataVm
                        .insertEpubReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

        fun persistPdfReadingData(
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
                runBlocking {
                    pdfReadingDataVm
                        .insertPdfReadingDataEntityAsync(this@apply)
                        .await()
                }
            }
        }

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

    }

}
