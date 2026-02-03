package com.porashona.studymaster.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.porashona.studymaster.data.dao.AcademicEventDao;
import com.porashona.studymaster.data.dao.AcademicEventDao_Impl;
import com.porashona.studymaster.data.dao.AchievementDao;
import com.porashona.studymaster.data.dao.AchievementDao_Impl;
import com.porashona.studymaster.data.dao.BlockedAppDao;
import com.porashona.studymaster.data.dao.BlockedAppDao_Impl;
import com.porashona.studymaster.data.dao.ChallengeDao;
import com.porashona.studymaster.data.dao.ChallengeDao_Impl;
import com.porashona.studymaster.data.dao.ExamDao;
import com.porashona.studymaster.data.dao.ExamDao_Impl;
import com.porashona.studymaster.data.dao.GoalDao;
import com.porashona.studymaster.data.dao.GoalDao_Impl;
import com.porashona.studymaster.data.dao.NoteDao;
import com.porashona.studymaster.data.dao.NoteDao_Impl;
import com.porashona.studymaster.data.dao.QuoteDao;
import com.porashona.studymaster.data.dao.QuoteDao_Impl;
import com.porashona.studymaster.data.dao.RoutineDao;
import com.porashona.studymaster.data.dao.RoutineDao_Impl;
import com.porashona.studymaster.data.dao.StudyResourceDao;
import com.porashona.studymaster.data.dao.StudyResourceDao_Impl;
import com.porashona.studymaster.data.dao.StudySessionDao;
import com.porashona.studymaster.data.dao.StudySessionDao_Impl;
import com.porashona.studymaster.data.dao.SubjectDao;
import com.porashona.studymaster.data.dao.SubjectDao_Impl;
import com.porashona.studymaster.data.dao.TaskDao;
import com.porashona.studymaster.data.dao.TaskDao_Impl;
import com.porashona.studymaster.data.dao.UserProfileDao;
import com.porashona.studymaster.data.dao.UserProfileDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudyDatabase_Impl extends StudyDatabase {
  private volatile StudySessionDao _studySessionDao;

  private volatile SubjectDao _subjectDao;

  private volatile RoutineDao _routineDao;

  private volatile AchievementDao _achievementDao;

  private volatile UserProfileDao _userProfileDao;

  private volatile GoalDao _goalDao;

  private volatile TaskDao _taskDao;

  private volatile NoteDao _noteDao;

  private volatile ExamDao _examDao;

  private volatile ChallengeDao _challengeDao;

  private volatile BlockedAppDao _blockedAppDao;

  private volatile QuoteDao _quoteDao;

  private volatile StudyResourceDao _studyResourceDao;

  private volatile AcademicEventDao _academicEventDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `study_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectId` INTEGER NOT NULL, `subjectName` TEXT NOT NULL, `durationInSeconds` INTEGER NOT NULL, `startTime` INTEGER NOT NULL, `endTime` INTEGER NOT NULL, `sessionType` TEXT NOT NULL, `completed` INTEGER NOT NULL, `xpEarned` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `subjects` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `shortName` TEXT NOT NULL, `colorHex` TEXT NOT NULL, `icon` TEXT NOT NULL, `totalTimeInSeconds` INTEGER NOT NULL, `totalSessions` INTEGER NOT NULL, `difficultyLevel` INTEGER NOT NULL, `targetHoursPerWeek` INTEGER NOT NULL, `chaptersTotal` INTEGER NOT NULL, `chaptersCompleted` INTEGER NOT NULL, `lastStudiedAt` INTEGER, `createdAt` INTEGER NOT NULL, `isArchived` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `routines` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subjectId` INTEGER NOT NULL, `subjectName` TEXT NOT NULL, `title` TEXT NOT NULL, `hour` INTEGER NOT NULL, `minute` INTEGER NOT NULL, `durationMinutes` INTEGER NOT NULL, `repeatType` TEXT NOT NULL, `repeatDays` TEXT NOT NULL, `isEnabled` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievements` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `iconName` TEXT NOT NULL, `xpReward` INTEGER NOT NULL, `isUnlocked` INTEGER NOT NULL, `unlockedAt` INTEGER, `progress` INTEGER NOT NULL, `targetProgress` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `totalXp` INTEGER NOT NULL, `level` INTEGER NOT NULL, `currentStreak` INTEGER NOT NULL, `longestStreak` INTEGER NOT NULL, `totalStudyTimeSeconds` INTEGER NOT NULL, `totalSessions` INTEGER NOT NULL, `lastStudyDate` INTEGER, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `goals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `targetMinutes` INTEGER NOT NULL, `currentMinutes` INTEGER NOT NULL, `subjectId` INTEGER, `subjectName` TEXT, `goalType` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `completedAt` INTEGER, `date` TEXT NOT NULL, `streakCount` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `subjectId` INTEGER, `subjectName` TEXT, `priority` TEXT NOT NULL, `dueDate` INTEGER, `dueTime` TEXT, `isCompleted` INTEGER NOT NULL, `isRecurring` INTEGER NOT NULL, `recurringType` TEXT NOT NULL, `parentTaskId` INTEGER, `xpReward` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `completedAt` INTEGER, `reminderEnabled` INTEGER NOT NULL, `reminderTime` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `htmlContent` TEXT NOT NULL, `subjectId` INTEGER, `subjectName` TEXT, `sessionId` INTEGER, `isFavorite` INTEGER NOT NULL, `color` TEXT NOT NULL, `imagesPaths` TEXT NOT NULL, `voiceNotePath` TEXT, `tags` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `exams` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `subjectId` INTEGER, `subjectName` TEXT, `examDate` INTEGER NOT NULL, `examTime` TEXT, `venue` TEXT NOT NULL, `notes` TEXT NOT NULL, `syllabus` TEXT NOT NULL, `preparationProgress` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `result` TEXT, `reflection` TEXT, `reminderEnabled` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `challenges` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `titleBn` TEXT NOT NULL, `description` TEXT NOT NULL, `descriptionBn` TEXT NOT NULL, `type` TEXT NOT NULL, `targetValue` INTEGER NOT NULL, `currentValue` INTEGER NOT NULL, `xpReward` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, `date` TEXT NOT NULL, `completedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `blocked_apps` (`packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `isBlocked` INTEGER NOT NULL, `isWhitelisted` INTEGER NOT NULL, `blockAttempts` INTEGER NOT NULL, `lastBlockedAt` INTEGER, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `block_statistics` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `blockedAt` INTEGER NOT NULL, `sessionId` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quotes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `textEn` TEXT NOT NULL, `textBn` TEXT NOT NULL, `author` TEXT NOT NULL, `authorBn` TEXT NOT NULL, `category` TEXT NOT NULL, `isFavorite` INTEGER NOT NULL, `isCustom` INTEGER NOT NULL, `shownCount` INTEGER NOT NULL, `lastShownAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `study_resources` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `url` TEXT NOT NULL, `type` TEXT NOT NULL, `subjectId` INTEGER, `subjectName` TEXT, `description` TEXT NOT NULL, `thumbnail` TEXT NOT NULL, `isFavorite` INTEGER NOT NULL, `visitCount` INTEGER NOT NULL, `lastVisitedAt` INTEGER, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `academic_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `eventType` TEXT NOT NULL, `date` INTEGER NOT NULL, `endDate` INTEGER, `time` TEXT, `subjectId` INTEGER, `subjectName` TEXT, `isHoliday` INTEGER NOT NULL, `reminderEnabled` INTEGER NOT NULL, `reminderMinutesBefore` INTEGER NOT NULL, `color` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '21ec511ef9b63a92b318c3480a5e9350')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `study_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `subjects`");
        db.execSQL("DROP TABLE IF EXISTS `routines`");
        db.execSQL("DROP TABLE IF EXISTS `achievements`");
        db.execSQL("DROP TABLE IF EXISTS `user_profile`");
        db.execSQL("DROP TABLE IF EXISTS `goals`");
        db.execSQL("DROP TABLE IF EXISTS `tasks`");
        db.execSQL("DROP TABLE IF EXISTS `notes`");
        db.execSQL("DROP TABLE IF EXISTS `exams`");
        db.execSQL("DROP TABLE IF EXISTS `challenges`");
        db.execSQL("DROP TABLE IF EXISTS `blocked_apps`");
        db.execSQL("DROP TABLE IF EXISTS `block_statistics`");
        db.execSQL("DROP TABLE IF EXISTS `quotes`");
        db.execSQL("DROP TABLE IF EXISTS `study_resources`");
        db.execSQL("DROP TABLE IF EXISTS `academic_events`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsStudySessions = new HashMap<String, TableInfo.Column>(9);
        _columnsStudySessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("subjectName", new TableInfo.Column("subjectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("durationInSeconds", new TableInfo.Column("durationInSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("startTime", new TableInfo.Column("startTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("endTime", new TableInfo.Column("endTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("sessionType", new TableInfo.Column("sessionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("completed", new TableInfo.Column("completed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudySessions.put("xpEarned", new TableInfo.Column("xpEarned", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudySessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudySessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudySessions = new TableInfo("study_sessions", _columnsStudySessions, _foreignKeysStudySessions, _indicesStudySessions);
        final TableInfo _existingStudySessions = TableInfo.read(db, "study_sessions");
        if (!_infoStudySessions.equals(_existingStudySessions)) {
          return new RoomOpenHelper.ValidationResult(false, "study_sessions(com.porashona.studymaster.data.model.StudySession).\n"
                  + " Expected:\n" + _infoStudySessions + "\n"
                  + " Found:\n" + _existingStudySessions);
        }
        final HashMap<String, TableInfo.Column> _columnsSubjects = new HashMap<String, TableInfo.Column>(14);
        _columnsSubjects.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("shortName", new TableInfo.Column("shortName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("totalTimeInSeconds", new TableInfo.Column("totalTimeInSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("totalSessions", new TableInfo.Column("totalSessions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("difficultyLevel", new TableInfo.Column("difficultyLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("targetHoursPerWeek", new TableInfo.Column("targetHoursPerWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("chaptersTotal", new TableInfo.Column("chaptersTotal", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("chaptersCompleted", new TableInfo.Column("chaptersCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("lastStudiedAt", new TableInfo.Column("lastStudiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubjects.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjects = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjects = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjects = new TableInfo("subjects", _columnsSubjects, _foreignKeysSubjects, _indicesSubjects);
        final TableInfo _existingSubjects = TableInfo.read(db, "subjects");
        if (!_infoSubjects.equals(_existingSubjects)) {
          return new RoomOpenHelper.ValidationResult(false, "subjects(com.porashona.studymaster.data.model.Subject).\n"
                  + " Expected:\n" + _infoSubjects + "\n"
                  + " Found:\n" + _existingSubjects);
        }
        final HashMap<String, TableInfo.Column> _columnsRoutines = new HashMap<String, TableInfo.Column>(11);
        _columnsRoutines.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("subjectName", new TableInfo.Column("subjectName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("hour", new TableInfo.Column("hour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("minute", new TableInfo.Column("minute", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("durationMinutes", new TableInfo.Column("durationMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("repeatType", new TableInfo.Column("repeatType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("repeatDays", new TableInfo.Column("repeatDays", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRoutines.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoutines = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoutines = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRoutines = new TableInfo("routines", _columnsRoutines, _foreignKeysRoutines, _indicesRoutines);
        final TableInfo _existingRoutines = TableInfo.read(db, "routines");
        if (!_infoRoutines.equals(_existingRoutines)) {
          return new RoomOpenHelper.ValidationResult(false, "routines(com.porashona.studymaster.data.model.Routine).\n"
                  + " Expected:\n" + _infoRoutines + "\n"
                  + " Found:\n" + _existingRoutines);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievements = new HashMap<String, TableInfo.Column>(9);
        _columnsAchievements.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("iconName", new TableInfo.Column("iconName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("isUnlocked", new TableInfo.Column("isUnlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("progress", new TableInfo.Column("progress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievements.put("targetProgress", new TableInfo.Column("targetProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievements = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievements = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievements = new TableInfo("achievements", _columnsAchievements, _foreignKeysAchievements, _indicesAchievements);
        final TableInfo _existingAchievements = TableInfo.read(db, "achievements");
        if (!_infoAchievements.equals(_existingAchievements)) {
          return new RoomOpenHelper.ValidationResult(false, "achievements(com.porashona.studymaster.data.model.Achievement).\n"
                  + " Expected:\n" + _infoAchievements + "\n"
                  + " Found:\n" + _existingAchievements);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfile = new HashMap<String, TableInfo.Column>(10);
        _columnsUserProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("totalXp", new TableInfo.Column("totalXp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("currentStreak", new TableInfo.Column("currentStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("longestStreak", new TableInfo.Column("longestStreak", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("totalStudyTimeSeconds", new TableInfo.Column("totalStudyTimeSeconds", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("totalSessions", new TableInfo.Column("totalSessions", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("lastStudyDate", new TableInfo.Column("lastStudyDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfile = new TableInfo("user_profile", _columnsUserProfile, _foreignKeysUserProfile, _indicesUserProfile);
        final TableInfo _existingUserProfile = TableInfo.read(db, "user_profile");
        if (!_infoUserProfile.equals(_existingUserProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profile(com.porashona.studymaster.data.model.UserProfile).\n"
                  + " Expected:\n" + _infoUserProfile + "\n"
                  + " Found:\n" + _existingUserProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsGoals = new HashMap<String, TableInfo.Column>(12);
        _columnsGoals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("targetMinutes", new TableInfo.Column("targetMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("currentMinutes", new TableInfo.Column("currentMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("goalType", new TableInfo.Column("goalType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoals.put("streakCount", new TableInfo.Column("streakCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGoals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGoals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGoals = new TableInfo("goals", _columnsGoals, _foreignKeysGoals, _indicesGoals);
        final TableInfo _existingGoals = TableInfo.read(db, "goals");
        if (!_infoGoals.equals(_existingGoals)) {
          return new RoomOpenHelper.ValidationResult(false, "goals(com.porashona.studymaster.data.model.Goal).\n"
                  + " Expected:\n" + _infoGoals + "\n"
                  + " Found:\n" + _existingGoals);
        }
        final HashMap<String, TableInfo.Column> _columnsTasks = new HashMap<String, TableInfo.Column>(17);
        _columnsTasks.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("priority", new TableInfo.Column("priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("dueDate", new TableInfo.Column("dueDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("dueTime", new TableInfo.Column("dueTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("isRecurring", new TableInfo.Column("isRecurring", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("recurringType", new TableInfo.Column("recurringType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("parentTaskId", new TableInfo.Column("parentTaskId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("reminderEnabled", new TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTasks.put("reminderTime", new TableInfo.Column("reminderTime", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTasks = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTasks = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTasks = new TableInfo("tasks", _columnsTasks, _foreignKeysTasks, _indicesTasks);
        final TableInfo _existingTasks = TableInfo.read(db, "tasks");
        if (!_infoTasks.equals(_existingTasks)) {
          return new RoomOpenHelper.ValidationResult(false, "tasks(com.porashona.studymaster.data.model.Task).\n"
                  + " Expected:\n" + _infoTasks + "\n"
                  + " Found:\n" + _existingTasks);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(14);
        _columnsNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("htmlContent", new TableInfo.Column("htmlContent", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("imagesPaths", new TableInfo.Column("imagesPaths", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("voiceNotePath", new TableInfo.Column("voiceNotePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(db, "notes");
        if (!_infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "notes(com.porashona.studymaster.data.model.Note).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsExams = new HashMap<String, TableInfo.Column>(15);
        _columnsExams.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("examDate", new TableInfo.Column("examDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("examTime", new TableInfo.Column("examTime", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("venue", new TableInfo.Column("venue", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("syllabus", new TableInfo.Column("syllabus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("preparationProgress", new TableInfo.Column("preparationProgress", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("result", new TableInfo.Column("result", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("reflection", new TableInfo.Column("reflection", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("reminderEnabled", new TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExams.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExams = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesExams = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoExams = new TableInfo("exams", _columnsExams, _foreignKeysExams, _indicesExams);
        final TableInfo _existingExams = TableInfo.read(db, "exams");
        if (!_infoExams.equals(_existingExams)) {
          return new RoomOpenHelper.ValidationResult(false, "exams(com.porashona.studymaster.data.model.Exam).\n"
                  + " Expected:\n" + _infoExams + "\n"
                  + " Found:\n" + _existingExams);
        }
        final HashMap<String, TableInfo.Column> _columnsChallenges = new HashMap<String, TableInfo.Column>(13);
        _columnsChallenges.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("titleBn", new TableInfo.Column("titleBn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("descriptionBn", new TableInfo.Column("descriptionBn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("targetValue", new TableInfo.Column("targetValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("currentValue", new TableInfo.Column("currentValue", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("xpReward", new TableInfo.Column("xpReward", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChallenges.put("completedAt", new TableInfo.Column("completedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChallenges = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChallenges = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChallenges = new TableInfo("challenges", _columnsChallenges, _foreignKeysChallenges, _indicesChallenges);
        final TableInfo _existingChallenges = TableInfo.read(db, "challenges");
        if (!_infoChallenges.equals(_existingChallenges)) {
          return new RoomOpenHelper.ValidationResult(false, "challenges(com.porashona.studymaster.data.model.Challenge).\n"
                  + " Expected:\n" + _infoChallenges + "\n"
                  + " Found:\n" + _existingChallenges);
        }
        final HashMap<String, TableInfo.Column> _columnsBlockedApps = new HashMap<String, TableInfo.Column>(7);
        _columnsBlockedApps.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("isBlocked", new TableInfo.Column("isBlocked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("isWhitelisted", new TableInfo.Column("isWhitelisted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("blockAttempts", new TableInfo.Column("blockAttempts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("lastBlockedAt", new TableInfo.Column("lastBlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockedApps.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlockedApps = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlockedApps = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlockedApps = new TableInfo("blocked_apps", _columnsBlockedApps, _foreignKeysBlockedApps, _indicesBlockedApps);
        final TableInfo _existingBlockedApps = TableInfo.read(db, "blocked_apps");
        if (!_infoBlockedApps.equals(_existingBlockedApps)) {
          return new RoomOpenHelper.ValidationResult(false, "blocked_apps(com.porashona.studymaster.data.model.BlockedApp).\n"
                  + " Expected:\n" + _infoBlockedApps + "\n"
                  + " Found:\n" + _existingBlockedApps);
        }
        final HashMap<String, TableInfo.Column> _columnsBlockStatistics = new HashMap<String, TableInfo.Column>(5);
        _columnsBlockStatistics.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockStatistics.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockStatistics.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockStatistics.put("blockedAt", new TableInfo.Column("blockedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlockStatistics.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlockStatistics = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlockStatistics = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlockStatistics = new TableInfo("block_statistics", _columnsBlockStatistics, _foreignKeysBlockStatistics, _indicesBlockStatistics);
        final TableInfo _existingBlockStatistics = TableInfo.read(db, "block_statistics");
        if (!_infoBlockStatistics.equals(_existingBlockStatistics)) {
          return new RoomOpenHelper.ValidationResult(false, "block_statistics(com.porashona.studymaster.data.model.BlockStatistic).\n"
                  + " Expected:\n" + _infoBlockStatistics + "\n"
                  + " Found:\n" + _existingBlockStatistics);
        }
        final HashMap<String, TableInfo.Column> _columnsQuotes = new HashMap<String, TableInfo.Column>(10);
        _columnsQuotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("textEn", new TableInfo.Column("textEn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("textBn", new TableInfo.Column("textBn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("author", new TableInfo.Column("author", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("authorBn", new TableInfo.Column("authorBn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("isCustom", new TableInfo.Column("isCustom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("shownCount", new TableInfo.Column("shownCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuotes.put("lastShownAt", new TableInfo.Column("lastShownAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuotes = new TableInfo("quotes", _columnsQuotes, _foreignKeysQuotes, _indicesQuotes);
        final TableInfo _existingQuotes = TableInfo.read(db, "quotes");
        if (!_infoQuotes.equals(_existingQuotes)) {
          return new RoomOpenHelper.ValidationResult(false, "quotes(com.porashona.studymaster.data.model.Quote).\n"
                  + " Expected:\n" + _infoQuotes + "\n"
                  + " Found:\n" + _existingQuotes);
        }
        final HashMap<String, TableInfo.Column> _columnsStudyResources = new HashMap<String, TableInfo.Column>(12);
        _columnsStudyResources.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("url", new TableInfo.Column("url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("thumbnail", new TableInfo.Column("thumbnail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("visitCount", new TableInfo.Column("visitCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("lastVisitedAt", new TableInfo.Column("lastVisitedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudyResources.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysStudyResources = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesStudyResources = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoStudyResources = new TableInfo("study_resources", _columnsStudyResources, _foreignKeysStudyResources, _indicesStudyResources);
        final TableInfo _existingStudyResources = TableInfo.read(db, "study_resources");
        if (!_infoStudyResources.equals(_existingStudyResources)) {
          return new RoomOpenHelper.ValidationResult(false, "study_resources(com.porashona.studymaster.data.model.StudyResource).\n"
                  + " Expected:\n" + _infoStudyResources + "\n"
                  + " Found:\n" + _existingStudyResources);
        }
        final HashMap<String, TableInfo.Column> _columnsAcademicEvents = new HashMap<String, TableInfo.Column>(14);
        _columnsAcademicEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("eventType", new TableInfo.Column("eventType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("time", new TableInfo.Column("time", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("subjectId", new TableInfo.Column("subjectId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("subjectName", new TableInfo.Column("subjectName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("isHoliday", new TableInfo.Column("isHoliday", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("reminderEnabled", new TableInfo.Column("reminderEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("reminderMinutesBefore", new TableInfo.Column("reminderMinutesBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("color", new TableInfo.Column("color", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAcademicEvents.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAcademicEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAcademicEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAcademicEvents = new TableInfo("academic_events", _columnsAcademicEvents, _foreignKeysAcademicEvents, _indicesAcademicEvents);
        final TableInfo _existingAcademicEvents = TableInfo.read(db, "academic_events");
        if (!_infoAcademicEvents.equals(_existingAcademicEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "academic_events(com.porashona.studymaster.data.model.AcademicEvent).\n"
                  + " Expected:\n" + _infoAcademicEvents + "\n"
                  + " Found:\n" + _existingAcademicEvents);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "21ec511ef9b63a92b318c3480a5e9350", "1f40ae9ebc4692e32c1b22bf527ba7ad");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "study_sessions","subjects","routines","achievements","user_profile","goals","tasks","notes","exams","challenges","blocked_apps","block_statistics","quotes","study_resources","academic_events");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `study_sessions`");
      _db.execSQL("DELETE FROM `subjects`");
      _db.execSQL("DELETE FROM `routines`");
      _db.execSQL("DELETE FROM `achievements`");
      _db.execSQL("DELETE FROM `user_profile`");
      _db.execSQL("DELETE FROM `goals`");
      _db.execSQL("DELETE FROM `tasks`");
      _db.execSQL("DELETE FROM `notes`");
      _db.execSQL("DELETE FROM `exams`");
      _db.execSQL("DELETE FROM `challenges`");
      _db.execSQL("DELETE FROM `blocked_apps`");
      _db.execSQL("DELETE FROM `block_statistics`");
      _db.execSQL("DELETE FROM `quotes`");
      _db.execSQL("DELETE FROM `study_resources`");
      _db.execSQL("DELETE FROM `academic_events`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(StudySessionDao.class, StudySessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SubjectDao.class, SubjectDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RoutineDao.class, RoutineDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AchievementDao.class, AchievementDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GoalDao.class, GoalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TaskDao.class, TaskDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExamDao.class, ExamDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChallengeDao.class, ChallengeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BlockedAppDao.class, BlockedAppDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuoteDao.class, QuoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StudyResourceDao.class, StudyResourceDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AcademicEventDao.class, AcademicEventDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public StudySessionDao studySessionDao() {
    if (_studySessionDao != null) {
      return _studySessionDao;
    } else {
      synchronized(this) {
        if(_studySessionDao == null) {
          _studySessionDao = new StudySessionDao_Impl(this);
        }
        return _studySessionDao;
      }
    }
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }

  @Override
  public RoutineDao routineDao() {
    if (_routineDao != null) {
      return _routineDao;
    } else {
      synchronized(this) {
        if(_routineDao == null) {
          _routineDao = new RoutineDao_Impl(this);
        }
        return _routineDao;
      }
    }
  }

  @Override
  public AchievementDao achievementDao() {
    if (_achievementDao != null) {
      return _achievementDao;
    } else {
      synchronized(this) {
        if(_achievementDao == null) {
          _achievementDao = new AchievementDao_Impl(this);
        }
        return _achievementDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }

  @Override
  public GoalDao goalDao() {
    if (_goalDao != null) {
      return _goalDao;
    } else {
      synchronized(this) {
        if(_goalDao == null) {
          _goalDao = new GoalDao_Impl(this);
        }
        return _goalDao;
      }
    }
  }

  @Override
  public TaskDao taskDao() {
    if (_taskDao != null) {
      return _taskDao;
    } else {
      synchronized(this) {
        if(_taskDao == null) {
          _taskDao = new TaskDao_Impl(this);
        }
        return _taskDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public ExamDao examDao() {
    if (_examDao != null) {
      return _examDao;
    } else {
      synchronized(this) {
        if(_examDao == null) {
          _examDao = new ExamDao_Impl(this);
        }
        return _examDao;
      }
    }
  }

  @Override
  public ChallengeDao challengeDao() {
    if (_challengeDao != null) {
      return _challengeDao;
    } else {
      synchronized(this) {
        if(_challengeDao == null) {
          _challengeDao = new ChallengeDao_Impl(this);
        }
        return _challengeDao;
      }
    }
  }

  @Override
  public BlockedAppDao blockedAppDao() {
    if (_blockedAppDao != null) {
      return _blockedAppDao;
    } else {
      synchronized(this) {
        if(_blockedAppDao == null) {
          _blockedAppDao = new BlockedAppDao_Impl(this);
        }
        return _blockedAppDao;
      }
    }
  }

  @Override
  public QuoteDao quoteDao() {
    if (_quoteDao != null) {
      return _quoteDao;
    } else {
      synchronized(this) {
        if(_quoteDao == null) {
          _quoteDao = new QuoteDao_Impl(this);
        }
        return _quoteDao;
      }
    }
  }

  @Override
  public StudyResourceDao studyResourceDao() {
    if (_studyResourceDao != null) {
      return _studyResourceDao;
    } else {
      synchronized(this) {
        if(_studyResourceDao == null) {
          _studyResourceDao = new StudyResourceDao_Impl(this);
        }
        return _studyResourceDao;
      }
    }
  }

  @Override
  public AcademicEventDao academicEventDao() {
    if (_academicEventDao != null) {
      return _academicEventDao;
    } else {
      synchronized(this) {
        if(_academicEventDao == null) {
          _academicEventDao = new AcademicEventDao_Impl(this);
        }
        return _academicEventDao;
      }
    }
  }
}
