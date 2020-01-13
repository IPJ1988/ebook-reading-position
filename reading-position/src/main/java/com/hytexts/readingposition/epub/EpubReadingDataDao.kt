package com.hytexts.readingposition.epub

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface EpubReadingDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(data: EpubReadingDataEntity)

}
