package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: Quote): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<Quote>)

    @Update
    suspend fun update(quote: Quote)

    @Delete
    suspend fun delete(quote: Quote)

    @Query("SELECT * FROM quotes ORDER BY shownCount ASC, RANDOM() LIMIT 1")
    suspend fun getRandomQuote(): Quote?

    @Query("SELECT * FROM quotes WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes WHERE isCustom = 1 ORDER BY id DESC")
    fun getCustomQuotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quotes ORDER BY id")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("UPDATE quotes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE quotes SET shownCount = shownCount + 1, lastShownAt = :time WHERE id = :id")
    suspend fun markAsShown(id: Long, time: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getQuotesCount(): Int

    @Query("DELETE FROM quotes")
    suspend fun deleteAll()
}