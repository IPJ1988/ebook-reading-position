package com.hytexts.readingposition.epub

import androidx.room.*

@Dao
interface EpubReadingDataDao {

    @Query("SELECT * FROM EpubReadingDataEntity")
    suspend fun getAllEpubReadingData(): List<EpubReadingDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: EpubReadingDataEntity)

    @Update
    suspend fun update(item: EpubReadingDataEntity)

    @Delete
    suspend fun delete(item: EpubReadingDataEntity)

    @Query("DELETE FROM EpubReadingDataEntity WHERE book_id = :bookId")
    suspend fun delete(bookId: String)

    @Query("DELETE FROM EpubReadingDataEntity")
    suspend fun deleteTable()

    @Query("SELECT * FROM EpubReadingDataEntity WHERE book_id = :bookId ")
    suspend fun findEpubReadingDataEntityByBookId(bookId: String): EpubReadingDataEntity?

}
