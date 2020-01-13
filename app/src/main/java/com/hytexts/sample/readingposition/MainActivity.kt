package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hytexts.readingposition.ReadingPositionHandler
import kotlinx.android.synthetic.main.activity_main.*

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

        btnInsertPdf?.setOnClickListener { insertPdfItem() }
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

    private fun insertEpubItem() {
        ReadingPositionHandler.persistEpubReadingData(
            this,
            "1234-5678",
            "menlo",
            2,
            .32f
        )
        getEpubItem()
    }

    private fun updateEpubItem() {
        getEpubItem()
    }

    private fun deleteEpubItem() {
        getEpubItem()
    }

    private fun insertPdfItem() {
        ReadingPositionHandler.persistPdfReadingData(
            this,
            "0011-1234",
            .34f
        )
    }

}
