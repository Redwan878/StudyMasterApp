package com.porashona.studymaster.data.dao

import androidx.room.*
import com.porashona.studymaster.data.model.Flashcard
import com.porashona.studymaster.data.model.FlashcardDeck
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    // ─── FlashcardDeck: Insert ───────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeck(deck: FlashcardDeck): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDecks(decks: List<FlashcardDeck>): List<Long>

    // ─── FlashcardDeck: Update ───────────────────────────────────────────

    @Update
    suspend fun updateDeck(deck: FlashcardDeck)

    // ─── FlashcardDeck: Delete ───────────────────────────────────────────

    @Delete
    suspend fun deleteDeck(deck: FlashcardDeck)

    @Query("DELETE FROM flashcard_decks WHERE id = :deckId")
    suspend fun deleteDeckById(deckId: Long)

    @Query("DELETE FROM flashcard_decks")
    suspend fun deleteAllDecks()

    // ─── FlashcardDeck: Read ─────────────────────────────────────────────

    @Query("SELECT * FROM flashcard_decks ORDER BY createdAt DESC")
    fun getAllDecks(): Flow<List<FlashcardDeck>>

    @Query("SELECT * FROM flashcard_decks WHERE id = :id")
    suspend fun getDeckById(id: Long): FlashcardDeck?

    @Query("SELECT * FROM flashcard_decks WHERE subjectId = :subjectId ORDER BY createdAt DESC")
    fun getDecksBySubject(subjectId: Long): Flow<List<FlashcardDeck>>

    @Query("SELECT * FROM flashcard_decks WHERE name LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchDecks(query: String): Flow<List<FlashcardDeck>>

    @Query("SELECT * FROM flashcard_decks WHERE isArchived = 0 ORDER BY lastStudiedAt DESC")
    fun getActiveDecks(): Flow<List<FlashcardDeck>>

    @Query("SELECT * FROM flashcard_decks WHERE isArchived = 1 ORDER BY createdAt DESC")
    fun getArchivedDecks(): Flow<List<FlashcardDeck>>

    // ─── FlashcardDeck: With Card Count ──────────────────────────────────

    data class DeckWithCardCount(
        val id: Long,
        val name: String,
        val subjectId: Long?,
        val subjectName: String?,
        val colorHex: String,
        val description: String,
        val isArchived: Boolean,
        val createdAt: Long,
        val lastStudiedAt: Long?,
        val cardCount: Int
    )

    @Query("""
        SELECT fd.*, COUNT(f.id) AS cardCount
        FROM flashcard_decks fd
        LEFT JOIN flashcards f ON fd.id = f.deckId
        WHERE fd.isArchived = 0
        GROUP BY fd.id
        ORDER BY fd.createdAt DESC
    """)
    fun getDecksWithCardCount(): Flow<List<DeckWithCardCount>>

    @Query("""
        SELECT fd.*, COUNT(f.id) AS cardCount
        FROM flashcard_decks fd
        LEFT JOIN flashcards f ON fd.id = f.deckId
        WHERE fd.subjectId = :subjectId AND fd.isArchived = 0
        GROUP BY fd.id
        ORDER BY fd.createdAt DESC
    """)
    fun getDecksWithCardCountBySubject(subjectId: Long): Flow<List<DeckWithCardCount>>

    // ─── FlashcardDeck: Utility ──────────────────────────────────────────

    @Query("UPDATE flashcard_decks SET isArchived = :isArchived WHERE id = :deckId")
    suspend fun setDeckArchived(deckId: Long, isArchived: Boolean)

    @Query("UPDATE flashcard_decks SET lastStudiedAt = :timestamp WHERE id = :deckId")
    suspend fun updateDeckLastStudied(deckId: Long, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM flashcard_decks")
    fun getDeckCount(): Flow<Int>

    // ─── Flashcard: Insert ───────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Flashcard): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<Flashcard>): List<Long>

    // ─── Flashcard: Update ───────────────────────────────────────────────

    @Update
    suspend fun updateCard(card: Flashcard)

    // ─── Flashcard: Delete ───────────────────────────────────────────────

    @Delete
    suspend fun deleteCard(card: Flashcard)

    @Query("DELETE FROM flashcards WHERE id = :cardId")
    suspend fun deleteCardById(cardId: Long)

    @Query("DELETE FROM flashcards WHERE deckId = :deckId")
    suspend fun deleteCardsByDeck(deckId: Long)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAllCards()

    // ─── Flashcard: Read ─────────────────────────────────────────────────

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId ORDER BY createdAt DESC")
    fun getCardsByDeck(deckId: Long): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE id = :id")
    suspend fun getCardById(id: Long): Flashcard?

    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    fun getAllCards(): Flow<List<Flashcard>>

    // ─── Flashcard: Spaced Repetition ────────────────────────────────────

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId AND (nextReviewAt IS NULL OR nextReviewAt <= :now) ORDER BY nextReviewAt ASC")
    fun getDueCardsForReview(deckId: Long, now: Long = System.currentTimeMillis()): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE (nextReviewAt IS NULL OR nextReviewAt <= :now) ORDER BY nextReviewAt ASC")
    fun getAllDueCardsForReview(now: Long = System.currentTimeMillis()): Flow<List<Flashcard>>

    @Query("""
        UPDATE flashcards
        SET nextReviewAt = :nextReviewAt,
            reviewCount = reviewCount + 1,
            correctCount = correctCount + CASE WHEN :wasCorrect = 1 THEN 1 ELSE 0 END,
            lastReviewAt = :lastReviewAt,
            difficulty = :difficulty
        WHERE id = :cardId
    """)
    suspend fun updateCardAfterReview(
        cardId: Long,
        nextReviewAt: Long,
        lastReviewAt: Long = System.currentTimeMillis(),
        wasCorrect: Boolean,
        difficulty: Int
    )

    // ─── Flashcard: Search ───────────────────────────────────────────────

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId AND front LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchCardsByFront(deckId: Long, query: String): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId AND back LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchCardsByBack(deckId: Long, query: String): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcards WHERE front LIKE '%' || :query || '%' OR back LIKE '%' || :query || '%' ORDER BY createdAt DESC")
    fun searchAllCards(query: String): Flow<List<Flashcard>>

    // ─── Flashcard: Count & Stats ────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM flashcards WHERE deckId = :deckId")
    suspend fun getCardCountByDeck(deckId: Long): Int

    @Query("""
        SELECT COUNT(*) FROM flashcards
        WHERE deckId = :deckId AND (nextReviewAt IS NULL OR nextReviewAt <= :now)
    """)
    suspend fun getDueCardCount(deckId: Long, now: Long = System.currentTimeMillis()): Int

    @Query("SELECT COUNT(*) FROM flashcards")
    fun getTotalCardCount(): Flow<Int>

    @Query("""
        SELECT SUM(correctCount) FROM flashcards WHERE deckId = :deckId
    """)
    suspend fun getCorrectCountByDeck(deckId: Long): Int?

    @Query("""
        SELECT SUM(reviewCount) FROM flashcards WHERE deckId = :deckId
    """)
    suspend fun getTotalReviewCountByDeck(deckId: Long): Int?

    // ─── Flashcard: Occlusion ────────────────────────────────────────────

    @Query("SELECT * FROM flashcards WHERE deckId = :deckId AND isOcclusion = 1 ORDER BY createdAt DESC")
    fun getOcclusionCards(deckId: Long): Flow<List<Flashcard>>
}