package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hytexts.readingposition.ReadingPositionHandler
import kotlinx.android.synthetic.main.activity_main.*
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

    private fun insertEpubItem() {
        ReadingPositionHandler.addEpubReadingData(
            this,
            "1234-5678-90",
            "menlo",
            2,
            .32f
        )
        getEpubItem()
    }

    private fun insertPdfItem() {
        ReadingPositionHandler.addPdfReadingData(
            this,
            "0011-1234-00",
            .34f
        )
        getPdfItem()
    }

    private fun getEpubItem(bookId: String = "1234-5678") {
        val item = ReadingPositionHandler.getEpubReadingData(this, bookId)
        Toast.makeText(
            this,
            """
                |font name: ${item?.fontName}
                |chapter index: ${item?.chapterIndex}
                |chapter position: ${item?.chapterPosition}
            """.trimMargin(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun getPdfItem(bookId: String = "0011-1234") {
        val item = ReadingPositionHandler.getPdfReadingData(this, bookId)
        Toast.makeText(
            this,
            "book progress: ${item?.bookPosition}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun updateEpubItem() {
        ReadingPositionHandler.updateEpubReadingData(
            this,
            "1234-5678",
            "update font",
            2,
            .32f
        )
        getEpubItem()
    }

    private fun updatePdfItem() {
        ReadingPositionHandler.updatePdfReadingData(
            this,
            "0011-1234",
            .53f
        )
        getEpubItem()
    }

    private fun deleteEpubItem() {
        ReadingPositionHandler.deleteEpubReadingData(
            this,
            "1234-5678"
        )
        getEpubItem()
    }

    private fun deletePdfItem() {
        ReadingPositionHandler.deletePdfReadingData(
            this,
            "0011-1234"
        )
        getEpubItem()
    }

    private fun findAllEpubItems() {
        val builder = StringBuilder()
        ReadingPositionHandler
            .getAllEpubReadingData(this)
            ?.forEach { builder.append(it).append("\n") }

        Toast.makeText(
            this,
            builder.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun findAllPdfItems() {
        val builder = StringBuilder()
        ReadingPositionHandler
            .getAllPdfReadingData(this)
            ?.forEach { builder.append(it).append("\n") }

        Toast.makeText(
            this,
            builder.toString(),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun clearDatabase() {
        ReadingPositionHandler.clearEpubDatabase(this)
        ReadingPositionHandler.clearPdfDatabase(this)
    }

}
