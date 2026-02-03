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
        AcademicEvent::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StudyDatabase : RoomDatabase() {

    // Existing DAOs
    abstract fun studySessionDao(): StudySessionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun routineDao(): RoutineDao
    abstract fun achievementDao(): AchievementDao
    abstract fun userProfileDao(): UserProfileDao

    // New DAOs
    abstract fun goalDao(): GoalDao
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
    abstract fun examDao(): ExamDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun quoteDao(): QuoteDao
    abstract fun studyResourceDao(): StudyResourceDao
    abstract fun academicEventDao(): AcademicEventDao

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