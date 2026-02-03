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
        UserProfile::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class StudyDatabase : RoomDatabase() {

    abstract fun studySessionDao(): StudySessionDao
    abstract fun subjectDao(): SubjectDao
    abstract fun routineDao(): RoutineDao
    abstract fun achievementDao(): AchievementDao
    abstract fun userProfileDao(): UserProfileDao

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