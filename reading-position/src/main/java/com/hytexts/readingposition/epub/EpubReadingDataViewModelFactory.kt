package com.hytexts.readingposition.epub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class EpubReadingDataViewModelFactory(
    private val dataSource: EpubReadingDataDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EpubReadingDataViewModel::class.java)) {
            return EpubReadingDataViewModel(
                dataSource
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}