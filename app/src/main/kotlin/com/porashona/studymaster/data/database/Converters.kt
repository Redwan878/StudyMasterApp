package com.porashona.studymaster.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.porashona.studymaster.data.model.*
import com.porashona.studymaster.ui.compose.components.ActivityType
import java.util.Date

class Converters {

    private val gson = Gson()

    // ═══════════════════════════════════════════════════════════════════════
    // Date Converters
    // ═══════════════════════════════════════════════════════════════════════

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // ═══════════════════════════════════════════════════════════════════════
    // Entity enum converters (used directly as field types in @Entity)
    // ═══════════════════════════════════════════════════════════════════════

    // SessionType (StudySession)
    @TypeConverter
    fun fromSessionType(value: SessionType): String = value.name

    @TypeConverter
    fun toSessionType(value: String): SessionType = SessionType.valueOf(value)

    // RepeatType (Routine)
    @TypeConverter
    fun fromRepeatType(value: RepeatType): String = value.name

    @TypeConverter
    fun toRepeatType(value: String): RepeatType = RepeatType.valueOf(value)

    // GoalType (Goal)
    @TypeConverter
    fun fromGoalType(value: GoalType): String = value.name

    @TypeConverter
    fun toGoalType(value: String): GoalType = GoalType.valueOf(value)

    // TaskPriority (Task)
    @TypeConverter
    fun fromTaskPriority(value: TaskPriority): String = value.name

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority = TaskPriority.valueOf(value)

    // RecurringType (Task)
    @TypeConverter
    fun fromRecurringType(value: RecurringType): String = value.name

    @TypeConverter
    fun toRecurringType(value: String): RecurringType = RecurringType.valueOf(value)

    // ChallengeType (Challenge entity)
    @TypeConverter
    fun fromChallengeType(value: ChallengeType): String = value.name

    @TypeConverter
    fun toChallengeType(value: String): ChallengeType = ChallengeType.valueOf(value)

    // ResourceType (StudyResource)
    @TypeConverter
    fun fromResourceType(value: ResourceType): String = value.name

    @TypeConverter
    fun toResourceType(value: String): ResourceType = ResourceType.valueOf(value)

    // EventType (AcademicEvent)
    @TypeConverter
    fun fromEventType(value: EventType): String = value.name

    @TypeConverter
    fun toEventType(value: String): EventType = EventType.valueOf(value)

    // ═══════════════════════════════════════════════════════════════════════
    // New entity / model enum converters
    // ═══════════════════════════════════════════════════════════════════════

    // FlashcardDifficulty (Flashcard)
    @TypeConverter
    fun fromFlashcardDifficulty(value: FlashcardDifficulty): String = value.name

    @TypeConverter
    fun toFlashcardDifficulty(value: String): FlashcardDifficulty =
        FlashcardDifficulty.valueOf(value)

    // QuestionDifficulty (QuestionBank — stored as String)
    @TypeConverter
    fun fromQuestionDifficulty(value: QuestionDifficulty): String = value.name

    @TypeConverter
    fun toQuestionDifficulty(value: String): QuestionDifficulty =
        QuestionDifficulty.valueOf(value)

    // QuestionType (QuestionBank — stored as String)
    @TypeConverter
    fun fromQuestionType(value: QuestionType): String = value.name

    @TypeConverter
    fun toQuestionType(value: String): QuestionType = QuestionType.valueOf(value)

    // FormulaCategory (Formula — stored as String)
    @TypeConverter
    fun fromFormulaCategory(value: FormulaCategory): String = value.name

    @TypeConverter
    fun toFormulaCategory(value: String): FormulaCategory =
        FormulaCategory.valueOf(value)

    // ChapterStatus (SyllabusChapter — stored as String)
    @TypeConverter
    fun fromChapterStatus(value: ChapterStatus): String = value.name

    @TypeConverter
    fun toChapterStatus(value: String): ChapterStatus = ChapterStatus.valueOf(value)

    // ExamType (SyllabusChapter — stored as String)
    @TypeConverter
    fun fromExamType(value: ExamType): String = value.name

    @TypeConverter
    fun toExamType(value: String): ExamType = ExamType.valueOf(value)

    // DailyChallengeType (Gamification.kt) is stored as a plain String in
    // DailyChallenge.challengeType, so no additional Room TypeConverter is
    // required.  The converter above handles the Challenge entity's
    // ChallengeType (from Challenge.kt).

    // BoardName (BoardQuestion — stored as String)
    @TypeConverter
    fun fromBoardName(value: BoardName): String = value.name

    @TypeConverter
    fun toBoardName(value: String): BoardName = BoardName.valueOf(value)

    // LockType (AppLockConfig — stored as String)
    @TypeConverter
    fun fromLockType(value: LockType): String = value.name

    @TypeConverter
    fun toLockType(value: String): LockType = LockType.valueOf(value)

    // BackupType (BackupRecord — stored as String)
    @TypeConverter
    fun fromBackupType(value: BackupType): String = value.name

    @TypeConverter
    fun toBackupType(value: String): BackupType = BackupType.valueOf(value)

    // NotificationType (NotificationPreference — stored as String)
    @TypeConverter
    fun fromNotificationType(value: NotificationType): String = value.name

    @TypeConverter
    fun toNotificationType(value: String): NotificationType =
        NotificationType.valueOf(value)

    // TimerMode — Gson-based serialisation (data class, not an enum)
    @TypeConverter
    fun fromTimerMode(value: TimerMode): String = gson.toJson(value)

    @TypeConverter
    fun toTimerMode(value: String): TimerMode? = runCatching {
        gson.fromJson(value, TimerMode::class.java)
    }.getOrNull()

    // ActivityType (Compose UI — uses dbValue property)
    @TypeConverter
    fun fromActivityType(value: ActivityType): String = value.dbValue

    @TypeConverter
    fun toActivityType(value: String): ActivityType = ActivityType.fromDb(value)

    // ═══════════════════════════════════════════════════════════════════════
    // Collection converters (Gson-based)
    // ═══════════════════════════════════════════════════════════════════════

    // List<Int> for repeat days
    @TypeConverter
    fun fromIntList(value: List<Int>): String = gson.toJson(value)

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    // List<String> for tags, image paths, etc.
    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}