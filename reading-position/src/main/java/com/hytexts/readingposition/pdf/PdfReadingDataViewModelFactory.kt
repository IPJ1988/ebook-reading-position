package com.hytexts.readingposition.pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class PdfReadingDataViewModelFactory(
    private val dataSource: PdfReadingDataDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PdfReadingDataViewModel::class.java)) {
            return PdfReadingDataViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}