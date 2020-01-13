package com.hytexts.readingposition.epub

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.withTransaction
import com.hytexts.readingposition.AppDatabase
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

    @Suppress("unused")
    suspend fun deleteAll(context: Context) {
        AppDatabase.getInstance(context).withTransaction {
            dataSource.deleteTable()
        }
    }

    @Suppress("unused")
    fun deleteAllAsync(): Deferred<Boolean> {
        return viewModelScope.async {
            try {
                dataSource.deleteTable()
                true
            } catch (e: Exception) {
                Log.e(LOG_TAG, "-> ${e.message}")
                if (BuildConfig.DEBUG) e.printStackTrace()
                false
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
