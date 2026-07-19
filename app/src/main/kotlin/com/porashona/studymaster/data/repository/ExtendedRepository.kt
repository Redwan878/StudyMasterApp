package com.porashona.studymaster.data.repository

import com.porashona.studymaster.data.dao.*
import com.porashona.studymaster.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.text.SimpleDateFormat
import java.util.*

class ExtendedRepository(
    private val goalDao: GoalDao,
    private val taskDao: TaskDao,
    private val noteDao: NoteDao,
    private val examDao: ExamDao,
    private val challengeDao: ChallengeDao,
    private val blockedAppDao: BlockedAppDao,
    private val quoteDao: QuoteDao,
    private val resourceDao: StudyResourceDao,
    private val eventDao: AcademicEventDao,
    private val userProfileDao: UserProfileDao
) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // ==================== GOALS ====================
    val allGoals: Flow<List<Goal>> = goalDao.getAllGoals()
    val activeGoals: Flow<List<Goal>> = goalDao.getActiveGoals()

    fun getDailyGoals(date: String = todayDate()): Flow<List<Goal>> = goalDao.getDailyGoals(date)

    suspend fun insertGoal(goal: Goal): Long = goalDao.insert(goal)

    suspend fun updateGoal(goal: Goal) = goalDao.update(goal)

    suspend fun deleteGoal(goal: Goal) = goalDao.delete(goal)

    suspend fun addMinutesToGoal(goalId: Long, minutes: Int) {
        goalDao.addMinutesToGoal(goalId, minutes)
        val goal = goalDao.getGoalById(goalId) ?: return
        if (goal.currentMinutes + minutes >= goal.targetMinutes && !goal.isCompleted) {
            goalDao.markAsCompleted(goalId)
            // Add XP reward
            userProfileDao.addXp(50)
        }
    }

    // ==================== TASKS ====================
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()
    val pendingTasks: Flow<List<Task>> = taskDao.getPendingTasks()
    val completedTasks: Flow<List<Task>> = taskDao.getCompletedTasks()
    val pendingTasksCount: Flow<Int> = taskDao.getPendingTasksCount()

    fun getSubtasks(parentId: Long): Flow<List<Task>> = taskDao.getSubtasks(parentId)

    fun getTasksBySubject(subjectId: Long): Flow<List<Task>> = taskDao.getTasksBySubject(subjectId)

    fun getOverdueTasks(): Flow<List<Task>> = taskDao.getOverdueTasks(System.currentTimeMillis())

    suspend fun insertTask(task: Task): Long = taskDao.insert(task)

    suspend fun updateTask(task: Task) = taskDao.update(task)

    suspend fun deleteTask(task: Task) = taskDao.delete(task)

    suspend fun completeTask(taskId: Long) {
        taskDao.markAsCompleted(taskId)
        val task = taskDao.getTaskById(taskId)
        task?.let {
            userProfileDao.addXp(it.xpReward)
        }
    }

    suspend fun uncompleteTask(taskId: Long) = taskDao.markAsIncomplete(taskId)

    // ==================== NOTES ====================
    val allNotes: Flow<List<Note>> = noteDao.getAllNotes()
    val favoriteNotes: Flow<List<Note>> = noteDao.getFavoriteNotes()

    fun getNotesBySubject(subjectId: Long): Flow<List<Note>> = noteDao.getNotesBySubject(subjectId)

    fun searchNotes(query: String): Flow<List<Note>> = noteDao.searchNotes(query)

    suspend fun insertNote(note: Note): Long = noteDao.insert(note)

    suspend fun updateNote(note: Note) = noteDao.update(note)

    suspend fun deleteNote(note: Note) = noteDao.delete(note)

    suspend fun toggleNoteFavorite(noteId: Long) {
        val note = noteDao.getNoteById(noteId) ?: return
        noteDao.setFavorite(noteId, !note.isFavorite)
    }

    // ==================== EXAMS ====================
    val allExams: Flow<List<Exam>> = examDao.getAllExams()
    val upcomingExams: Flow<List<Exam>> = examDao.getUpcomingExams(System.currentTimeMillis())
    val completedExams: Flow<List<Exam>> = examDao.getCompletedExams()

    fun getExamsBySubject(subjectId: Long): Flow<List<Exam>> = examDao.getExamsBySubject(subjectId)

    suspend fun insertExam(exam: Exam): Long = examDao.insert(exam)

    suspend fun updateExam(exam: Exam) = examDao.update(exam)

    suspend fun deleteExam(exam: Exam) = examDao.delete(exam)

    suspend fun updateExamProgress(examId: Long, progress: Int) = examDao.updateProgress(examId, progress)

    suspend fun completeExam(examId: Long, result: String?, reflection: String?) {
        examDao.markAsCompleted(examId, result, reflection)
    }

    fun getExamCountdown(examDate: Long): Long {
        return examDate - System.currentTimeMillis()
    }

    // ==================== CHALLENGES ====================
    fun getDailyChallenges(date: String = todayDate()): Flow<List<Challenge>> =
        challengeDao.getDailyChallenges(date)

    val completedChallenges: Flow<List<Challenge>> = challengeDao.getCompletedChallenges()

    suspend fun initializeDailyChallenges() {
        val today = todayDate()
        val existing = challengeDao.getDailyChallenges(today).first()
        if (existing.isEmpty()) {
            val challenges = DailyChallenges.generateForDate(today)
            challengeDao.insertAll(challenges)
        }
    }

    suspend fun updateChallengeProgress(challengeId: String, value: Int) {
        challengeDao.updateProgress(challengeId, value)
        val challenge = challengeDao.getChallengeById(challengeId) ?: return
        if (value >= challenge.targetValue && !challenge.isCompleted) {
            challengeDao.markAsCompleted(challengeId)
            userProfileDao.addXp(challenge.xpReward)
        }
    }

    // ==================== BLOCKED APPS ====================
    val allBlockedApps: Flow<List<BlockedApp>> = blockedAppDao.getAllBlockedApps()
    val activeBlockedApps: Flow<List<BlockedApp>> = blockedAppDao.getActiveBlockedApps()
    val whitelistedApps: Flow<List<BlockedApp>> = blockedAppDao.getWhitelistedApps()
    val totalBlockAttempts: Flow<Int?> = blockedAppDao.getTotalBlockAttempts()

    suspend fun addBlockedApp(app: BlockedApp) = blockedAppDao.insert(app)

    suspend fun updateBlockedApp(app: BlockedApp) = blockedAppDao.update(app)

    suspend fun removeBlockedApp(app: BlockedApp) = blockedAppDao.delete(app)

    suspend fun toggleAppBlocked(packageName: String, isBlocked: Boolean) {
        blockedAppDao.setBlocked(packageName, isBlocked)
    }

    suspend fun toggleAppWhitelisted(packageName: String, isWhitelisted: Boolean) {
        blockedAppDao.setWhitelisted(packageName, isWhitelisted)
    }

    suspend fun recordBlockAttempt(packageName: String, sessionId: Long? = null) {
        blockedAppDao.incrementBlockAttempt(packageName)
        val app = blockedAppDao.getByPackageName(packageName)
        app?.let {
            blockedAppDao.insertBlockStat(
                BlockStatistic(
                    packageName = packageName,
                    appName = it.appName,
                    sessionId = sessionId
                )
            )
        }
    }

    fun getRecentBlockStats(limit: Int = 50): Flow<List<BlockStatistic>> =
        blockedAppDao.getRecentBlockStats(limit)

    fun getMostBlockedApps(since: Long): Flow<List<AppBlockCount>> =
        blockedAppDao.getMostBlockedApps(since)

    // ==================== QUOTES ====================
    val allQuotes: Flow<List<Quote>> = quoteDao.getAllQuotes()
    val favoriteQuotes: Flow<List<Quote>> = quoteDao.getFavoriteQuotes()
    val customQuotes: Flow<List<Quote>> = quoteDao.getCustomQuotes()

    suspend fun initializeQuotes() {
        val count = quoteDao.getQuotesCount()
        if (count == 0) {
            quoteDao.insertAll(DefaultQuotes.quotes)
        }
    }

    suspend fun getRandomQuote(): Quote? {
        val quote = quoteDao.getRandomQuote()
        quote?.let { quoteDao.markAsShown(it.id) }
        return quote
    }

    suspend fun addCustomQuote(textEn: String, textBn: String, author: String = "") {
        quoteDao.insert(
            Quote(
                textEn = textEn,
                textBn = textBn,
                author = author,
                authorBn = author,
                isCustom = true
            )
        )
    }

    suspend fun toggleQuoteFavorite(quoteId: Long) {
        val quotes = allQuotes.first()
        val quote = quotes.find { it.id == quoteId } ?: return
        quoteDao.setFavorite(quoteId, !quote.isFavorite)
    }

    suspend fun deleteQuote(quote: Quote) = quoteDao.delete(quote)

    // ==================== STUDY RESOURCES ====================
    val allResources: Flow<List<StudyResource>> = resourceDao.getAllResources()
    val favoriteResources: Flow<List<StudyResource>> = resourceDao.getFavoriteResources()

    fun getResourcesBySubject(subjectId: Long): Flow<List<StudyResource>> =
        resourceDao.getResourcesBySubject(subjectId)

    fun getResourcesByType(type: ResourceType): Flow<List<StudyResource>> =
        resourceDao.getResourcesByType(type)

    fun searchResources(query: String): Flow<List<StudyResource>> =
        resourceDao.searchResources(query)

    suspend fun insertResource(resource: StudyResource): Long = resourceDao.insert(resource)

    suspend fun updateResource(resource: StudyResource) = resourceDao.update(resource)

    suspend fun deleteResource(resource: StudyResource) = resourceDao.delete(resource)

    suspend fun visitResource(resourceId: Long) = resourceDao.incrementVisitCount(resourceId)

    suspend fun toggleResourceFavorite(resourceId: Long) {
        val resource = resourceDao.getResourceById(resourceId) ?: return
        resourceDao.setFavorite(resourceId, !resource.isFavorite)
    }

    // ==================== ACADEMIC EVENTS ====================
    val allEvents: Flow<List<AcademicEvent>> = eventDao.getAllEvents()
    val upcomingEvents: Flow<List<AcademicEvent>> = eventDao.getUpcomingEvents(System.currentTimeMillis())
    val holidays: Flow<List<AcademicEvent>> = eventDao.getHolidays()

    fun getEventsInRange(startDate: Long, endDate: Long): Flow<List<AcademicEvent>> =
        eventDao.getEventsInRange(startDate, endDate)

    fun getEventsBySubject(subjectId: Long): Flow<List<AcademicEvent>> =
        eventDao.getEventsBySubject(subjectId)

    suspend fun insertEvent(event: AcademicEvent): Long = eventDao.insert(event)

    suspend fun updateEvent(event: AcademicEvent) = eventDao.update(event)

    suspend fun deleteEvent(event: AcademicEvent) = eventDao.delete(event)

    // ==================== UTILITIES ====================
    private fun todayDate(): String = dateFormat.format(Date())

    fun formatCountdown(milliseconds: Long): String {
        if (milliseconds <= 0) return "সময় শেষ!"

        val days = milliseconds / (1000 * 60 * 60 * 24)
        val hours = (milliseconds % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minutes = (milliseconds % (1000 * 60 * 60)) / (1000 * 60)

        return when {
            days > 0 -> "${days}দিন ${hours}ঘণ্টা"
            hours > 0 -> "${hours}ঘণ্টা ${minutes}মিনিট"
            else -> "${minutes}মিনিট"
        }
    }
}