package com.hytexts.readingposition.pdf

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class PdfReadingDataViewModel(private val dataSource: PdfReadingDataDao) : ViewModel() {

    companion object {
        private val LOG_TAG = PdfReadingDataViewModel::class.java.simpleName
    }

    private val pdfReadingDataJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + pdfReadingDataJob)

    override fun onCleared() {
        super.onCleared()
        pdfReadingDataJob.cancel()
    }

    fun insertPdfReadingDataEntityAsync(item: PdfReadingDataEntity): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.insert(item)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                e.printStackTrace()
                false
            }
        }
    }

    fun updatePdfReadingDataEntityAsync(item: PdfReadingDataEntity): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.update(item)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                e.printStackTrace()
                false
            }
        }
    }

    fun deletePdfReadingDataEntityAsync(bookId: String): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.delete(bookId)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun clear() {
        dataSource.deleteTable()
    }

    fun findAllPdfReadingDataEntityAsync(): Deferred<List<PdfReadingDataEntity>?> {
        return viewModelScope.async {
            try {
                dataSource.getAllPdfReadingData()
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

    fun findPdfReadingDataEntityAsync(bookId: String): Deferred<PdfReadingDataEntity?> {
        return viewModelScope.async {
            try {
                dataSource.findPdfReadingDataEntityByBookId(bookId)
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                e.printStackTrace()
                null
            }
        }
    }

}
