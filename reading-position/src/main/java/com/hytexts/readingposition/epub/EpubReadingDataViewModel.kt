package com.hytexts.readingposition.epub

import android.util.Log
import androidx.lifecycle.ViewModel
import com.hytexts.readingposition.BuildConfig
import kotlinx.coroutines.*

class EpubReadingDataViewModel(private val dataSource: EpubReadingDataDao) : ViewModel() {

    companion object {
        private val LOG_TAG = EpubReadingDataViewModel::class.java.simpleName
    }

    private val epubReadingDataJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + epubReadingDataJob)

    override fun onCleared() {
        super.onCleared()
        epubReadingDataJob.cancel()
    }

    fun insertEpubReadingDataEntityAsync(data: EpubReadingDataEntity): Deferred<Boolean> {
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