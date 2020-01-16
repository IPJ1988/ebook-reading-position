package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hytexts.readingposition.ReadingPositionHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setEvent()
    }

    private fun setEvent() {
        btnInsertEpub?.setOnClickListener { insertEpubItem() }
        btnUpdateEpub?.setOnClickListener { updateEpubItem() }
        btnClearEpub?.setOnClickListener { deleteEpubItem() }
        btnFindAllEpub?.setOnClickListener { findAllEpubItems() }

        btnInsertPdf?.setOnClickListener { insertPdfItem() }
        btnUpdatePdf?.setOnClickListener { updatePdfItem() }
        btnClearPdf?.setOnClickListener { deletePdfItem() }
        btnFindAllPdf?.setOnClickListener { findAllPdfItems() }

        btnClearDatabase?.setOnClickListener { clearDatabase() }
    }

    private fun insertEpubItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity)
            .addEpubReadingData(
                "1234-5678-90",
                "menlo",
                2,
                .32f
            )
        getEpubItem()
    }

    private fun insertPdfItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity)
            .addPdfReadingData("0011-1234-00", .34f)
        getPdfItem()
    }

    private fun getEpubItem(bookId: String = "1234-5678-90") = runBlocking {
        val item = ReadingPositionHandler(this@MainActivity).getEpubReadingData(bookId)
        Toast.makeText(
            this@MainActivity,
            """
                |font name: ${item?.fontName}
                |chapter index: ${item?.chapterIndex}
                |chapter position: ${item?.chapterPosition}
            """.trimMargin(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getPdfItem(bookId: String = "0011-1234-00") = runBlocking {
        val item = ReadingPositionHandler(this@MainActivity).getPdfReadingData(bookId)
        Toast.makeText(
            this@MainActivity,
            "book progress: ${item?.bookPosition}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun updateEpubItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity).updateEpubReadingData(
            "1234-5678-90",
            "update font",
            2,
            .32f
        )
        getEpubItem()
    }

    private fun updatePdfItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity)
            .updatePdfReadingData("0011-1234-00", .53f)
        getPdfItem()
    }

    private fun deleteEpubItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity)
            .deleteEpubReadingData("1234-5678-90")
        getEpubItem()
    }

    private fun deletePdfItem() = runBlocking {
        ReadingPositionHandler(this@MainActivity)
            .deletePdfReadingData("0011-1234-00")
        getPdfItem()
    }

    private fun findAllEpubItems() = runBlocking {
        val builder = StringBuilder()
        ReadingPositionHandler(this@MainActivity)
            .getAllEpubReadingData()
            ?.forEach { builder.append(it).append("\n") }

        Toast.makeText(
            this@MainActivity,
            builder.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun findAllPdfItems() = runBlocking {
        val builder = StringBuilder()
        ReadingPositionHandler(this@MainActivity)
            .getAllPdfReadingData()
            ?.forEach { builder.append(it).append("\n") }

        Toast.makeText(
            this@MainActivity,
            builder.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun clearDatabase() {
        ReadingPositionHandler(this).apply {
            clearEpubDatabase()
            clearPdfDatabase()
        }
    }

}
