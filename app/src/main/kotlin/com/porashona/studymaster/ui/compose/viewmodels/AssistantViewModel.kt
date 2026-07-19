package com.porashona.studymaster.ui.compose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.porashona.studymaster.data.dao.FormulaDao
import com.porashona.studymaster.data.dao.NoteDao
import com.porashona.studymaster.data.dao.PracticeTestDao
import com.porashona.studymaster.data.dao.StudySessionDao
import com.porashona.studymaster.data.dao.SubjectDao
import com.porashona.studymaster.data.dao.SyllabusChapterDao
import com.porashona.studymaster.data.model.Formula
import com.porashona.studymaster.data.model.Note
import com.porashona.studymaster.data.model.QuestionBank
import com.porashona.studymaster.data.model.SyllabusChapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Data Classes ─────────────────────────────────────────────────────────

data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val relatedFormula: Formula? = null,
    val relatedQuestions: List<QuestionBank>? = null,
    val isTyping: Boolean = false
)

data class WeakTopic(
    val subjectName: String,
    val chapterName: String,
    val averageScore: Double,
    val questionCount: Int
)

// ─── ViewModel ────────────────────────────────────────────────────────────

@HiltViewModel
class AssistantViewModel @Inject constructor(
    private val formulaDao: FormulaDao,
    private val noteDao: NoteDao,
    private val syllabusChapterDao: SyllabusChapterDao,
    private val practiceTestDao: PracticeTestDao,
    private val studySessionDao: StudySessionDao,
    private val subjectDao: SubjectDao
) : ViewModel() {

    // ─── Chat State ──────────────────────────────────────────────────────
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    // ─── Suggested Topics ───────────────────────────────────────────────
    private val _suggestedTopics = MutableStateFlow<List<String>>(emptyList())
    val suggestedTopics: StateFlow<List<String>> = _suggestedTopics.asStateFlow()

    // ─── Weak Topics ────────────────────────────────────────────────────
    private val _weakTopics = MutableStateFlow<List<WeakTopic>>(emptyList())
    val weakTopics: StateFlow<List<WeakTopic>> = _weakTopics.asStateFlow()

    // ─── Subjects ───────────────────────────────────────────────────────
    val subjects: StateFlow<List<com.porashona.studymaster.data.model.Subject>> = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // ─── Events ─────────────────────────────────────────────────────────
    private val _events = MutableSharedFlow<AssistantEvent>(extraBufferCapacity = 16)
    val events = _events.asSharedFlow()

    private var messageIdCounter = 0L

    init {
        loadSuggestedTopics()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Chat Functions
    // ═══════════════════════════════════════════════════════════════════════

    fun solveDoubt(query: String) {
        addMessage(query, isFromUser = true)
        showTyping()

        viewModelScope.launch {
            val response = generateDoubtResponse(query)
            hideTyping()
            addMessage(response, isFromUser = false)
        }
    }

    fun explainFormula(formulaId: Long) {
        viewModelScope.launch {
            val formula = formulaDao.getById(formulaId) ?: return@launch
            showTyping()

            val explanation = buildString {
                append("📐 **${formula.formulaText}**\n\n")
                append("**বিবরণ:** ${formula.description}\n\n")
                formula.chapterName?.let { append("অধ্যায়: $it\n") }
                formula.subjectName?.let { append("বিষয়: $it\n") }
                append("\n")
                append("এই সূত্রটি ${formula.subjectName ?: "পদার্থবিজ্ঞান"}-এর ${formula.chapterName ?: "মূল অধ্যায়"}-এ ব্যবহৃত হয়।")
            }

            hideTyping()
            addMessage(explanation, isFromUser = false, relatedFormula = formula)
        }
    }

    fun generateMCQs(subjectId: Long, chapterName: String?, count: Int = 5) {
        showTyping()

        viewModelScope.launch {
            val questions = practiceTestDao.getRandomQuestions(subjectId, count)

            hideTyping()

            if (questions.isEmpty()) {
                addMessage("এই বিষয়ে কোনো প্রশ্ন পাওয়া যায়নি। আগে প্রশ্ন যোগ করুন।", isFromUser = false)
                return@launch
            }

            val response = buildString {
                append("📝 **Auto-Generated MCQ (${questions.size}টি)**\n\n")
                questions.forEachIndexed { index, q ->
                    append("${index + 1}. ${q.questionText}\n")
                    append("   ক) ${q.optionA}\n")
                    append("   খ) ${q.optionB}\n")
                    append("   গ) ${q.optionC}\n")
                    append("   ঘ) ${q.optionD}\n\n")
                }
                append("💡 উত্তর দেখতে নিচের বোতামে ক্লিক করুন।")
            }

            addMessage(response, isFromUser = false, relatedQuestions = questions)
        }
    }

    fun summarizeNote(noteId: Long) {
        showTyping()

        viewModelScope.launch {
            val note = noteDao.getNoteById(noteId) ?: run {
                hideTyping()
                addMessage("নোট পাওয়া যায়নি।", isFromUser = false)
                return@launch
            }

            // Simple extractive summarization: take first sentence of each paragraph
            val content = note.content
            val sentences = content.split(Regex("[।\\n]+"))
                .map { it.trim() }
                .filter { it.isNotBlank() }

            val summaryLength = maxOf(3, sentences.size / 3)
            val summary = sentences.take(summaryLength).joinToString("। ") + "।"

            hideTyping()

            val response = buildString {
                append("📋 **সারাংশ: ${note.title}**\n\n")
                append(summary)
                if (sentences.size > summaryLength) {
                    append("\n\n_...এবং আরও ${sentences.size - summaryLength}টি বাক্য।_")
                }
            }

            addMessage(response, isFromUser = false)
        }
    }

    fun getAnswerFeedback(
        questionText: String,
        selectedOption: Int,
        correctOption: Int,
        explanation: String
    ) {
        val isCorrect = selectedOption == correctOption
        val optionLabels = mapOf(1 to "ক", 2 to "খ", 3 to "গ", 4 to "ঘ")

        val response = if (isCorrect) {
            "✅ **সঠিক!** আপনার উত্তর সঠিক (${optionLabels[selectedOption]})।\n\n$explanation"
        } else {
            "❌ **ভুল!** আপনার উত্তর: ${optionLabels[selectedOption]}। সঠিক উত্তর: ${optionLabels[correctOption]}।\n\n**ব্যাখ্যা:** $explanation"
        }

        addMessage(response, isFromUser = false)
    }

    fun voiceInput(transcript: String) {
        addMessage(transcript, isFromUser = true)
        solveDoubt(transcript)
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Weak Topic Detection
    // ═══════════════════════════════════════════════════════════════════════

    fun detectWeakTopics() {
        viewModelScope.launch {
            // Analyze based on: syllabus completion + practice test scores
            val completionData = syllabusChapterDao.getAllCompletionPercentages().first()
            val weakList = mutableListOf<WeakTopic>()

            for (completion in completionData) {
                if (completion.percentage < 70.0) {
                    weakList.add(
                        WeakTopic(
                            subjectName = completion.subjectName ?: "Unknown",
                            chapterName = "সিলেবাস সম্পূর্ণ হয়নি",
                            averageScore = completion.percentage,
                            questionCount = completion.totalChapters - completion.completedChapters
                        )
                    )
                }
            }

            // Also check practice test results for weak chapters
            val recentResults = practiceTestDao.getRecentResults(20).first()
            // Group by chapter and find low-scoring ones
            // (This would be more sophisticated with chapter-level test tracking)

            _weakTopics.value = weakList.sortedBy { it.averageScore }
            _events.emit(AssistantEvent.WeakTopicsDetected(weakList.size))
        }
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Clear
    // ═══════════════════════════════════════════════════════════════════════

    fun clearChat() {
        _chatMessages.value = emptyList()
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Private Helpers
    // ═══════════════════════════════════════════════════════════════════════

    private fun addMessage(text: String, isFromUser: Boolean, relatedFormula: Formula? = null, relatedQuestions: List<QuestionBank>? = null) {
        val message = ChatMessage(
            id = "msg_${messageIdCounter++}",
            text = text,
            isFromUser = isFromUser,
            relatedFormula = relatedFormula,
            relatedQuestions = relatedQuestions
        )
        _chatMessages.value = _chatMessages.value + message
    }

    private fun showTyping() {
        _isTyping.value = true
    }

    private fun hideTyping() {
        _isTyping.value = false
    }

    private suspend fun generateDoubtResponse(query: String): String {
        // Search formulas first
        val formulaResults = formulaDao.search(query).first()
        if (formulaResults.isNotEmpty()) {
            val formula = formulaResults.first()
            return buildString {
                append("📐 আপনার প্রশ্নের সাথে সম্পর্কিত সূত্র পাওয়া গেছে:\n\n")
                append("**${formula.formulaText}**\n")
                append("${formula.description}\n\n")
                if (formula.chapterName != null) append("অধ্যায়: ${formula.chapterName}\n")
                if (formula.subjectName != null) append("বিষয়: ${formula.subjectName}\n")
                if (formulaResults.size > 1) {
                    append("\nআরও ${formulaResults.size - 1}টি সূত্র পাওয়া গেছে। বিস্তারিত দেখতে জিজ্ঞাসা করুন।")
                }
            }
        }

        // Search notes
        val noteResults = noteDao.searchNotes(query).first()
        if (noteResults.isNotEmpty()) {
            val note = noteResults.first()
            val preview = note.content.take(300)
            return buildString {
                append("📚 আপনার নোটে এই বিষয়ে কিছু পাওয়া গেছে:\n\n")
                append("**${note.title}**\n")
                append("$preview...\n\n")
                append("সম্পূর্ণ নোট দেখতে নোট সেকশনে যান।")
            }
        }

        // Search syllabus chapters
        val allChapters = syllabusChapterDao.getAllChapters().first()
        val matchingChapters = allChapters.filter {
            it.chapterName.contains(query, ignoreCase = true) ||
                    it.notes.contains(query, ignoreCase = true)
        }
        if (matchingChapters.isNotEmpty()) {
            return buildString {
                append("📖 সম্পর্কিত অধ্যায় পাওয়া গেছে:\n\n")
                matchingChapters.take(3).forEach { chapter ->
                    val status = when (chapter.status) {
                        "COMPLETED" -> "✅"
                        "IN_PROGRESS" -> "🔄"
                        else -> "⬜"
                    }
                    append("$status অধ্যায় ${chapter.chapterNumber}: ${chapter.chapterName}")
                    chapter.subjectName?.let { append(" ($it)") }
                    append("\n")
                }
            }
        }

        return "দুঃখিত, \"${query}\" সম্পর্কে কিছু পাওয়া যায়নি। অনুগ্রহ করে ভিন্ন কিছু জিজ্ঞাসা করুন বা নিজে নোট যোগ করুন।"
    }

    private fun loadSuggestedTopics() {
        viewModelScope.launch {
            val topics = mutableListOf<String>()
            val chapters = syllabusChapterDao.getAllChapters().first()
            val inProgress = chapters.filter { it.status == "IN_PROGRESS" }.take(3)
            inProgress.forEach {
                topics.add("${it.chapterName} রিভিশন করুন")
            }

            // Add general suggestions
            topics.addAll(listOf(
                "আজকের পড়াশোনার পরিকল্পনা কী?",
                "দুর্বল বিষয়গুলো কী কী?",
                "আগামী পরীক্ষার প্রস্তুতি কেমন?"
            ))

            _suggestedTopics.value = topics.distinct()
        }
    }
}

// ─── Events ───────────────────────────────────────────────────────────────

sealed class AssistantEvent {
    data class WeakTopicsDetected(val count: Int) : AssistantEvent()
}