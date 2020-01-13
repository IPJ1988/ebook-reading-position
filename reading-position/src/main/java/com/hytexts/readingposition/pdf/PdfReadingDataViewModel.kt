package com.hytexts.readingposition.pdf

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hytexts.readingposition.BuildConfig
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

    fun insertPdfReadingDataEntityAsync(data: PdfReadingDataEntity): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.add(data)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
            }
        }
    }

}