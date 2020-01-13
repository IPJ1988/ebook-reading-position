package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import androidx.lifecycle.ViewModelProviders
import com.hytexts.readingposition.DatabaseInjection
import com.hytexts.readingposition.epub.EpubReadingDataEntity
import com.hytexts.readingposition.epub.EpubReadingDataViewModel
import com.hytexts.readingposition.pdf.PdfReadingDataEntity
import com.hytexts.readingposition.pdf.PdfReadingDataViewModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var epubReadingDataVm: EpubReadingDataViewModel
    private lateinit var pdfReadingDataVm: PdfReadingDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        EpubReadingDataEntity(
            "1234-5678",
            "menlo",
            2,
            0.25f,
            System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS
        ).apply {
            runBlocking {
                epubReadingDataVm
                    .insertEpubReadingDataEntityAsync(this@apply)
                    .await()
            }
        }

        PdfReadingDataEntity(
            "5678-1234",
            0.34f,
            System.currentTimeMillis() / DateUtils.SECOND_IN_MILLIS
        ).apply {
            runBlocking {
                pdfReadingDataVm
                    .insertPdfReadingDataEntityAsync(this@apply)
                    .await()
            }
        }
    }

    private fun setupViewModel() {
        DatabaseInjection
            .getEpubReadingDataViewModelFactory(this)
            .apply {
                epubReadingDataVm = ViewModelProviders
                    .of(this@MainActivity, this)
                    .get(EpubReadingDataViewModel::class.java)
            }

        DatabaseInjection
            .getPdfReadingDataViewModelFactory(this)
            .apply {
                pdfReadingDataVm = ViewModelProviders
                    .of(this@MainActivity, this)
                    .get(PdfReadingDataViewModel::class.java)
            }
    }

}
