package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.FlashcardDao
import com.porashona.studymaster.data.dao.NoteDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.model.Flashcard
import com.porashona.studymaster.data.model.FlashcardDeck
import com.porashona.studymaster.data.model.FlashcardDifficulty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.Locale
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class ReviewStats(
    val totalReviewed: Int = 0,
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val accuracy: Float = 0f
)

data class DeckStats(
    val totalCards: Int = 0,
    val dueCards: Int = 0,
    val masteredCards: Int = 0,
    val averageDifficulty: Float = 0f
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class FlashcardViewModel @Inject constructor(
    private val flashcardDao: FlashcardDao,
    private val noteDao: NoteDao,
    private val subjectDao: SubjectDao
) : ViewModel() {

    // ─── Decks ──────────────────────────────────────────────────────────
    val decks: StateFlow<List<FlashcardDao.DeckWithCardCount>> = flashcardDao.getDecksWithCardCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Current Deck ───────────────────────────────────────────────────
    private val _currentDeckId = MutableStateFlow<Long?>(null)
    val currentDeckId: StateFlow<Long?> = _currentDeckId.asStateFlow()

    val currentDeck: StateFlow<FlashcardDeck?> = _currentDeckId
        .map { id -> if (id != null) flashcardDao.getDeckById(id) else null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // ─── Cards ──────────────────────────────────────────────────────────
    private val _deckCards = MutableStateFlow<List<Flashcard>>(emptyList())
    val deckCards: StateFlow<List<Flashcard>> = _deckCards.asStateFlow()

    private val _dueCards = MutableStateFlow<List<Flashcard>>(emptyList())
    val dueCards: StateFlow<List<Flashcard>> = _dueCards.asStateFlow()

    // ─── Review Session ─────────────────────────────────────────────────
    private val _reviewSessionActive = MutableStateFlow(false)
    val reviewSessionActive: StateFlow<Boolean> = _reviewSessionActive.asStateFlow()

    private val _currentCard = MutableStateFlow<Flashcard?>(null)
    val currentCard: StateFlow<Flashcard?> = _currentCard.asStateFlow()

    private val _currentCardIndex = MutableStateFlow(0)
    val currentCardIndex: StateFlow<Int> = _currentCardIndex.asStateFlow()

    // ─── Stats ──────────────────────────────────────────────────────────
    private val _reviewStats = MutableStateFlow(ReviewStats())
    val reviewStats: StateFlow<ReviewStats> = _reviewStats.asStateFlow()

    private val _deckStats = MutableStateFlow<DeckStats?>(null)
    val deckStats: StateFlow<DeckStats?> = _deckStats.asStateFlow()

    // ─── Subjects ───────────────────────────────────────────────────────
    val subjects: StateFlow<List<com.porashona.studymaster.data.model.Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Events ─────────────────────────────────────────────────────────
    private val _events = MutableSharedFlow<FlashcardEvent>(extraBufferCapacity = 16)
    val events = _events.asSharedFlow()

    // ═══════════════════════════════════════════════════════════════════════
    // Deck CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun createDeck(
        name: String,
        subjectId: Long? = null,
        subjectName: String? = null,
        colorHex: String = "#6C63FF",
        description: String = ""
    ) {
        viewModelScope.launch {
            val resolvedName = if (subjectName == null && subjectId != null) {
                subjectDao.getSubjectById(subjectId)?.name
            } else {
                subjectName
            }
            val deck = FlashcardDeck(
                name = name,
                subjectId = subjectId,
                subjectName = resolvedName,
                colorHex = colorHex,
                description = description
            )
            val deckId = flashcardDao.insertDeck(deck)
            _events.emit(FlashcardEvent.DeckCreated(deckId))
        }
    }

    fun updateDeck(deck: FlashcardDeck) {
        viewModelScope.launch {
            flashcardDao.updateDeck(deck)
        }
    }

    fun deleteDeck(deckId: Long) {
        viewModelScope.launch {
            flashcardDao.deleteDeckById(deckId)
            if (_currentDeckId.value == deckId) {
                _currentDeckId.value = null
                _deckCards.value = emptyList()
                _reviewStats.value = ReviewStats()
            }
            _events.emit(FlashcardEvent.DeckDeleted(deckId))
        }
    }

    fun selectDeck(deckId: Long) {
        _currentDeckId.value = deckId
        viewModelScope.launch {
            loadDeckCards(deckId)
            loadDueCards(deckId)
            loadDeckStats(deckId)
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Card CRUD
    // ═══════════════════════════════════════════════════════════════════════

    fun addCard(front: String, back: String, deckId: Long, imageUrl: String? = null) {
        viewModelScope.launch {
            val card = Flashcard(
                deckId = deckId,
                front = front,
                back = back,
                imageUrl = imageUrl
            )
            flashcardDao.insertCard(card)
            loadDeckCards(deckId)
            loadDueCards(deckId)
            loadDeckStats(deckId)
        }
    }

    fun updateCard(card: Flashcard) {
        viewModelScope.launch {
            flashcardDao.updateCard(card)
        }
    }

    fun deleteCard(cardId: Long) {
        viewModelScope.launch {
            flashcardDao.deleteCardById(cardId)
            _currentDeckId.value?.let { loadDeckCards(it) }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Review Session (Spaced Repetition)
    // ═══════════════════════════════════════════════════════════════════════

    fun startReview(deckId: Long) {
        viewModelScope.launch {
            val due = flashcardDao.getDueCardsForReview(deckId).first()
            if (due.isEmpty()) {
                _events.emit(FlashcardEvent.NoDueCards)
                return@launch
            }
            _dueCards.value = due
            _currentCardIndex.value = 0
            _currentCard.value = due.first()
            _reviewSessionActive.value = true
            _reviewStats.value = ReviewStats()
            flashcardDao.updateDeckLastStudied(deckId)
            _events.emit(FlashcardEvent.ReviewStarted(due.size))
        }
    }

    fun rateCard(difficulty: FlashcardDifficulty) {
        viewModelScope.launch {
            val card = _currentCard.value ?: return@launch
            val wasCorrect = difficulty in listOf(FlashcardDifficulty.GOOD, FlashcardDifficulty.EASY)
            val diffValue = when (difficulty) {
                FlashcardDifficulty.AGAIN -> 1
                FlashcardDifficulty.HARD -> 2
                FlashcardDifficulty.GOOD -> 3
                FlashcardDifficulty.EASY -> 4
            }

            // Calculate next review time using SM-2 inspired intervals
            val now = System.currentTimeMillis()
            val intervalMillis = when (difficulty) {
                FlashcardDifficulty.AGAIN -> 1 * 60 * 1000L          // 1 minute
                FlashcardDifficulty.HARD -> 10 * 60 * 1000L         // 10 minutes
                FlashcardDifficulty.GOOD -> 24 * 60 * 60 * 1000L    // 1 day
                FlashcardDifficulty.EASY -> 3 * 24 * 60 * 60 * 1000L // 3 days
            }

            flashcardDao.updateCardAfterReview(
                cardId = card.id,
                nextReviewAt = now + intervalMillis,
                wasCorrect = wasCorrect,
                difficulty = diffValue
            )

            // Update stats
            val stats = _reviewStats.value
            _reviewStats.value = ReviewStats(
                totalReviewed = stats.totalReviewed + 1,
                correctCount = stats.correctCount + if (wasCorrect) 1 else 0,
                wrongCount = stats.wrongCount + if (!wasCorrect) 1 else 0,
                accuracy = if (stats.totalReviewed + 1 > 0) {
                    ((stats.correctCount + if (wasCorrect) 1 else 0).toFloat() / (stats.totalReviewed + 1)) * 100
                } else 0f
            )

            // Move to next card
            advanceToNextCard()
        }
    }

    private fun advanceToNextCard() {
        val currentIndex = _currentCardIndex.value
        val dueList = _dueCards.value
        val nextIndex = currentIndex + 1

        if (nextIndex >= dueList.size) {
            // Review session complete
            _reviewSessionActive.value = false
            _currentCard.value = null
            _events.emit(FlashcardEvent.ReviewCompleted(_reviewStats.value))
        } else {
            _currentCardIndex.value = nextIndex
            _currentCard.value = dueList[nextIndex]
        }
    }

    fun skipCard() {
        viewModelScope.launch { advanceToNextCard() }
    }

    fun flipCard() {
        // UI-only action; handled in Compose by toggling a local state
    }

    fun endReview() {
        viewModelScope.launch {
            _reviewSessionActive.value = false
            _currentCard.value = null
            _currentDeckId.value?.let { loadDueCards(it) }
            _events.emit(FlashcardEvent.ReviewCompleted(_reviewStats.value))
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Quick Add from Note
    // ═══════════════════════════════════════════════════════════════════════

    fun quickAddFromNote(noteId: Long, deckId: Long) {
        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: return@launch
            val content = note.content
            if (content.isBlank()) return@launch

            // Parse note content for "Q: ... A: ..." patterns
            val pairs = parseQAPairs(content)
            if (pairs.isEmpty()) {
                _events.emit(FlashcardEvent.QuickAddFailed("নোটে কোনো Q/A পেয়ার পাওয়া যায়নি"))
                return@launch
            }

            val cards = pairs.map { (front, back) ->
                Flashcard(
                    deckId = deckId,
                    front = front,
                    back = back
                )
            }
            flashcardDao.insertCards(cards)
            loadDeckCards(deckId)
            loadDueCards(deckId)
            _events.emit(FlashcardEvent.CardsAddedFromNote(cards.size))
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Export / Import
    // ═══════════════════════════════════════════════════════════════════════

    fun exportDeck(deckId: Long, filePath: String) {
        viewModelScope.launch {
            val deck = flashcardDao.getDeckById(deckId) ?: return@launch
            val cards = flashcardDao.getCardsByDeck(deckId).first()

            val jsonArray = JSONArray()
            cards.forEach { card ->
                val json = JSONObject().apply {
                    put("front", card.front)
                    put("back", card.back)
                    put("imageUrl", card.imageUrl ?: "")
                    put("difficulty", card.difficulty)
                }
                jsonArray.put(json)
            }

            val exportJson = JSONObject().apply {
                put("deckName", deck.name)
                put("subjectName", deck.subjectName ?: "")
                put("cards", jsonArray)
            }

            try {
                File(filePath).writeText(exportJson.toString(2))
                _events.emit(FlashcardEvent.ExportSuccess(cards.size))
            } catch (e: Exception) {
                _events.emit(FlashcardEvent.ExportFailed(e.message ?: "Unknown error"))
            }
        }
    }

    fun importDeck(deckId: Long, jsonString: String) {
        viewModelScope.launch {
            try {
                val json = JSONObject(jsonString)
                val cardsArray = json.optJSONArray("cards") ?: JSONArray()

                val cards = (0 until cardsArray.length()).map { i ->
                    val cardJson = cardsArray.getJSONObject(i)
                    Flashcard(
                        deckId = deckId,
                        front = cardJson.getString("front"),
                        back = cardJson.getString("back"),
                        imageUrl = cardJson.optString("imageUrl", "").ifBlank { null },
                        difficulty = cardJson.optInt("difficulty", 0)
                    )
                }

                flashcardDao.insertCards(cards)
                loadDeckCards(deckId)
                loadDueCards(deckId)
                _events.emit(FlashcardEvent.ImportSuccess(cards.size))
            } catch (e: Exception) {
                _events.emit(FlashcardEvent.ImportFailed(e.message ?: "Invalid JSON"))
            }
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Private Helpers
    // ═══════════════════════════════════════════════════════════════════════

    private suspend fun loadDeckCards(deckId: Long) {
        val cards = flashcardDao.getCardsByDeck(deckId).first()
        _deckCards.value = cards
    }

    private suspend fun loadDueCards(deckId: Long) {
        val due = flashcardDao.getDueCardsForReview(deckId).first()
        _dueCards.value = due
    }

    private suspend fun loadDeckStats(deckId: Long) {
        val totalCards = flashcardDao.getCardCountByDeck(deckId)
        val dueCount = flashcardDao.getDueCardCount(deckId)
        val correctCount = flashcardDao.getCorrectCountByDeck(deckId) ?: 0
        val mastered = if (totalCards > 0) {
            flashcardDao.getCardsByDeck(deckId).first()
                .count { it.correctCount > 0 && it.reviewCount >= 3 }
        } else 0

        _deckStats.value = DeckStats(
            totalCards = totalCards,
            dueCards = dueCount,
            masteredCards = mastered
        )
    }

    private fun parseQAPairs(content: String): List<Pair<String, String>> {
        val pairs = mutableListOf<Pair<String, String>>()
        // Support multiple formats:
        // Q: ... A: ...
        // Question: ... Answer: ...
        // প্রশ্ন: ... উত্তর: ...

        val patterns = listOf(
            Regex("""Q:\s*(.+?)\s*A:\s*(.+?)(?=Q:|$)""", RegexOption.DOT_MATCHES_ALL),
            Regex("""Question:\s*(.+?)\s*Answer:\s*(.+?)(?=Question:|$)""", RegexOption.DOT_MATCHES_ALL),
            Regex("""প্রশ্ন:\s*(.+?)\s*উত্তর:\s*(.+?)(?=প্রশ্ন:|$)""", RegexOption.DOT_MATCHES_ALL)
        )

        for (pattern in patterns) {
            val matches = pattern.findAll(content)
            for (match in matches) {
                pairs.add(
                    match.groupValues[1].trim() to match.groupValues[2].trim()
                )
            }
            if (pairs.isNotEmpty()) break
        }

        // Fallback: split by double newline
        if (pairs.isEmpty() && content.contains("\n\n")) {
            val blocks = content.split("\n\n")
            for (i in 0 until blocks.size - 1 step 2) {
                val front = blocks.getOrNull(i)?.trim() ?: continue
                val back = blocks.getOrNull(i + 1)?.trim() ?: continue
                pairs.add(front to back)
            }
        }

        return pairs
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class FlashcardEvent {
    data class DeckCreated(val deckId: Long) : FlashcardEvent()
    data class DeckDeleted(val deckId: Long) : FlashcardEvent()
    object NoDueCards : FlashcardEvent()
    data class ReviewStarted(val cardCount: Int) : FlashcardEvent()
    data class ReviewCompleted(val stats: ReviewStats) : FlashcardEvent()
    data class CardsAddedFromNote(val count: Int) : FlashcardEvent()
    data class QuickAddFailed(val reason: String) : FlashcardEvent()
    data class ExportSuccess(val cardCount: Int) : FlashcardEvent()
    data class ExportFailed(val error: String) : FlashcardEvent()
    data class ImportSuccess(val cardCount: Int) : FlashcardEvent()
    data class ImportFailed(val error: String) : FlashcardEvent()
}