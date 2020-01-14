package com.hytexts.readingposition

import android.content.Context
import com.hytexts.readingposition.epub.EpubReadingDataViewModelFactory
import com.hytexts.readingposition.pdf.PdfReadingDataViewModelFactory

object DatabaseInjection {

    fun getEpubReadingDataViewModelFactory(context: Context) =
        EpubReadingDataViewModelFactory(
            AppDatabase.getInstance(context).epubReadingDataDao()
        )

    fun getPdfReadingDataViewModelFactory(context: Context) =
        PdfReadingDataViewModelFactory(
            AppDatabase.getInstance(context).pdfReadingDataDao()
        )

}
