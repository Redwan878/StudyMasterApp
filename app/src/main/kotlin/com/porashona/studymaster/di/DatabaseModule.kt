package com.porashona.studymaster.di

import android.content.Context
import com.porashona.studymaster.data.dao.*
import com.porashona.studymaster.data.database.StudyDatabase
import com.porashona.studymaster.data.preferences.PreferencesManager
import com.porashona.studymaster.data.repository.ExtendedRepository
import com.porashona.studymaster.data.repository.StudyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Central Hilt module that provides every database-related singleton:
 *
 * - [StudyDatabase]              – the Room database instance
 * - 21 DAOs                      – one per entity group
 * - [PreferencesManager]         – DataStore-backed preferences
 * - [StudyRepository]            – core session / subject / routine repo
 * - [ExtendedRepository]         – goals, tasks, notes, exams, etc.
 *
 * All bindings are `@Singleton` and installed in [SingletonComponent] so
 * they survive for the lifetime of the application process.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // ═══════════════════════════════════════════════════════════════════════
    // Database
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun provideStudyDatabase(@ApplicationContext context: Context): StudyDatabase =
        StudyDatabase.getDatabase(context)

    // ═══════════════════════════════════════════════════════════════════════
    // DAOs — Core
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun provideStudySessionDao(database: StudyDatabase): StudySessionDao =
        database.studySessionDao()

    @Provides
    @Singleton
    fun provideSubjectDao(database: StudyDatabase): SubjectDao =
        database.subjectDao()

    @Provides
    @Singleton
    fun provideRoutineDao(database: StudyDatabase): RoutineDao =
        database.routineDao()

    @Provides
    @Singleton
    fun provideAchievementDao(database: StudyDatabase): AchievementDao =
        database.achievementDao()

    @Provides
    @Singleton
    fun provideUserProfileDao(database: StudyDatabase): UserProfileDao =
        database.userProfileDao()

    // ═══════════════════════════════════════════════════════════════════════
    // DAOs — Extended
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun provideGoalDao(database: StudyDatabase): GoalDao =
        database.goalDao()

    @Provides
    @Singleton
    fun provideTaskDao(database: StudyDatabase): TaskDao =
        database.taskDao()

    @Provides
    @Singleton
    fun provideNoteDao(database: StudyDatabase): NoteDao =
        database.noteDao()

    @Provides
    @Singleton
    fun provideExamDao(database: StudyDatabase): ExamDao =
        database.examDao()

    @Provides
    @Singleton
    fun provideChallengeDao(database: StudyDatabase): ChallengeDao =
        database.challengeDao()

    @Provides
    @Singleton
    fun provideBlockedAppDao(database: StudyDatabase): BlockedAppDao =
        database.blockedAppDao()

    @Provides
    @Singleton
    fun provideQuoteDao(database: StudyDatabase): QuoteDao =
        database.quoteDao()

    @Provides
    @Singleton
    fun provideStudyResourceDao(database: StudyDatabase): StudyResourceDao =
        database.studyResourceDao()

    @Provides
    @Singleton
    fun provideAcademicEventDao(database: StudyDatabase): AcademicEventDao =
        database.academicEventDao()

    // ═══════════════════════════════════════════════════════════════════════
    // DAOs — New feature modules
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun provideFlashcardDao(database: StudyDatabase): FlashcardDao =
        database.flashcardDao()

    @Provides
    @Singleton
    fun providePracticeTestDao(database: StudyDatabase): PracticeTestDao =
        database.practiceTestDao()

    @Provides
    @Singleton
    fun provideFormulaDao(database: StudyDatabase): FormulaDao =
        database.formulaDao()

    @Provides
    @Singleton
    fun provideSyllabusChapterDao(database: StudyDatabase): SyllabusChapterDao =
        database.syllabusChapterDao()

    @Provides
    @Singleton
    fun provideGamificationDao(database: StudyDatabase): GamificationDao =
        database.gamificationDao()

    @Provides
    @Singleton
    fun provideCollaborationDao(database: StudyDatabase): CollaborationDao =
        database.collaborationDao()

    @Provides
    @Singleton
    fun provideMediaResourceDao(database: StudyDatabase): MediaResourceDao =
        database.mediaResourceDao()

    @Provides
    @Singleton
    fun provideBoardQuestionDao(database: StudyDatabase): BoardQuestionDao =
        database.boardQuestionDao()

    @Provides
    @Singleton
    fun provideBackupDao(database: StudyDatabase): BackupDao =
        database.backupDao()

    @Provides
    @Singleton
    fun provideAppLockDao(database: StudyDatabase): AppLockDao =
        database.appLockDao()

    // ═══════════════════════════════════════════════════════════════════════
    // Preferences
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context): PreferencesManager =
        PreferencesManager(context)

    // ═══════════════════════════════════════════════════════════════════════
    // Repositories
    // ═══════════════════════════════════════════════════════════════════════

    @Provides
    @Singleton
    fun provideStudyRepository(
        studySessionDao: StudySessionDao,
        subjectDao: SubjectDao,
        routineDao: RoutineDao,
        achievementDao: AchievementDao,
        userProfileDao: UserProfileDao
    ): StudyRepository = StudyRepository(
        sessionDao = studySessionDao,
        subjectDao = subjectDao,
        routineDao = routineDao,
        achievementDao = achievementDao,
        profileDao = userProfileDao
    )

    @Provides
    @Singleton
    fun provideExtendedRepository(
        goalDao: GoalDao,
        taskDao: TaskDao,
        noteDao: NoteDao,
        examDao: ExamDao,
        challengeDao: ChallengeDao,
        blockedAppDao: BlockedAppDao,
        quoteDao: QuoteDao,
        studyResourceDao: StudyResourceDao,
        academicEventDao: AcademicEventDao,
        userProfileDao: UserProfileDao
    ): ExtendedRepository = ExtendedRepository(
        goalDao = goalDao,
        taskDao = taskDao,
        noteDao = noteDao,
        examDao = examDao,
        challengeDao = challengeDao,
        blockedAppDao = blockedAppDao,
        quoteDao = quoteDao,
        resourceDao = studyResourceDao,
        eventDao = academicEventDao,
        userProfileDao = userProfileDao
    )
}