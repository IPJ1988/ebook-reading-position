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
                dataSource.insert(data)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
            }
        }
    }

    fun updateEpubReadingDataEntityAsync(item: EpubReadingDataEntity): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.update(item)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
            }
        }
    }

    fun deleteEpubReadingDataEntityAsync(bookId: String): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.delete(bookId)
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
            }
        }
    }

    suspend fun clear() {
        dataSource.deleteTable()
    }

    fun findAllEpubReadingDataEntityAsync(): Deferred<List<EpubReadingDataEntity>?> {
        return viewModelScope.async {
            try {
                dataSource.getAllEpubReadingData()
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                null
            }
        }
    }

    fun findEpubReadingDataEntityAsync(bookId: String): Deferred<EpubReadingDataEntity?> {
        return viewModelScope.async {
            try {
                dataSource.findEpubReadingDataEntityByBookId(bookId)
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                null
            }
        }
    }

}
