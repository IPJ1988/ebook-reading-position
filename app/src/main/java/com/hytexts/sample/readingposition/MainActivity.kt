package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hytexts.readingposition.ReadingPositionHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        ReadingPositionHandler.persistEpubReadingData(
            this,
            "1234-5678",
            "menlo",
            2,
            .32f
        )

        ReadingPositionHandler.persistPdfReadingData(
            this,
            "0011-1234",
            .34f
        )
    }

}
