package com.hytexts.sample.readingposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hytexts.readingposition.Sample

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Sample().sayHi()
    }
}
