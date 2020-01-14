package com.hytexts.readingposition.pdf

import androidx.room.*

@Dao
interface PdfReadingDataDao {

    @Query("SELECT * FROM PdfReadingDataEntity")
    suspend fun getAllPdfReadingData(): List<PdfReadingDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PdfReadingDataEntity)

    @Update
    suspend fun update(item: PdfReadingDataEntity)

    @Delete
    suspend fun delete(item: PdfReadingDataEntity)

    @Query("DELETE FROM PdfReadingDataEntity WHERE book_id = :bookId")
    suspend fun delete(bookId: String)

    @Query("DELETE FROM PdfReadingDataEntity")
    suspend fun deleteTable()

    @Query("SELECT * FROM PdfReadingDataEntity WHERE book_id = :bookId ")
    suspend fun findPdfReadingDataEntityByBookId(bookId: String): PdfReadingDataEntity?

}
