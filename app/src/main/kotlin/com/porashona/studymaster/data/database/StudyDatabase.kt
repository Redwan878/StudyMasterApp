package com.porashona.studymaster.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.porashona.studymaster.data.dao.*
import com.porashona.studymaster.data.model.*

@Database(
    entities = [
        // ── Original entities ──────────────────────────────────────────────
        StudySession::class,
        Subject::class,
        Routine::class,
        Achievement::class,
        UserProfile::class,
        Goal::class,
        Task::class,
        Note::class,
        Exam::class,
        Challenge::class,
        BlockedApp::class,
        BlockStatistic::class,
        Quote::class,
        StudyResource::class,
        AcademicEvent::class,

        // ── Flashcard entities ─────────────────────────────────────────────
        FlashcardDeck::class,
        Flashcard::class,

        // ── Practice test entities ─────────────────────────────────────────
        QuestionBank::class,
        PracticeTest::class,
        PracticeTestResult::class,

        // ── Formula ────────────────────────────────────────────────────────
        Formula::class,

        // ── Syllabus ───────────────────────────────────────────────────────
        SyllabusChapter::class,

        // ── Gamification entities ──────────────────────────────────────────
        XPGain::class,
        UserLevel::class,
        DailyChallenge::class,

        // ── Collaboration entities ─────────────────────────────────────────
        StudyRoom::class,
        SharedNote::class,
        DiscussionPost::class,

        // ── Media resource entities ────────────────────────────────────────
        VideoLink::class,
        AudioLecture::class,
        DiagramEntry::class,

        // ── Board question ─────────────────────────────────────────────────
        BoardQuestion::class,

        // ── App lock ───────────────────────────────────────────────────────
        AppLockConfig::class,

        // ── Backup & notification preference ───────────────────────────────
        BackupRecord::class,
        NotificationPreference::class,
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StudyDatabase : RoomDatabase() {

    // ── Original DAOs ─────────────────────────────────────────────────────
    abstract fun studySessionDao(): StudySessionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun routineDao(): RoutineDao
    abstract fun achievementDao(): AchievementDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun examDao(): ExamDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun quoteDao(): QuoteDao
    abstract fun studyResourceDao(): StudyResourceDao
    abstract fun academicEventDao(): AcademicEventDao

    // ── Flashcard DAO ─────────────────────────────────────────────────────
    abstract fun flashcardDao(): FlashcardDao

    // ── Practice test DAO ─────────────────────────────────────────────────
    abstract fun practiceTestDao(): PracticeTestDao

    // ── Formula DAO ───────────────────────────────────────────────────────
    abstract fun formulaDao(): FormulaDao

    // ── Syllabus chapter DAO ──────────────────────────────────────────────
    abstract fun syllabusChapterDao(): SyllabusChapterDao

    // ── Gamification DAO ──────────────────────────────────────────────────
    abstract fun gamificationDao(): GamificationDao

    // ── Collaboration DAO ─────────────────────────────────────────────────
    abstract fun collaborationDao(): CollaborationDao

    // ── Media resource DAO ────────────────────────────────────────────────
    abstract fun mediaResourceDao(): MediaResourceDao

    // ── Board question DAO ────────────────────────────────────────────────
    abstract fun boardQuestionDao(): BoardQuestionDao

    // ── Backup DAO (BackupRecord + NotificationPreference) ────────────────
    abstract fun backupDao(): BackupDao

    // ── App lock DAO ──────────────────────────────────────────────────────
    abstract fun appLockDao(): AppLockDao

    companion object {
        @Volatile
        private var INSTANCE: StudyDatabase? = null

        fun getDatabase(context: Context): StudyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudyDatabase::class.java,
                    "study_master_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}