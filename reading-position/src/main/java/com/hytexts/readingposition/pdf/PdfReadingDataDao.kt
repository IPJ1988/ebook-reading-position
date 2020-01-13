package com.hytexts.readingposition.pdf

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface PdfReadingDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(data: PdfReadingDataEntity)

}
