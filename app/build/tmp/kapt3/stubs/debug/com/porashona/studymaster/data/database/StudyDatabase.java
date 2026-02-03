package com.porashona.studymaster.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u0018H&J\b\u0010\u0019\u001a\u00020\u001aH&J\b\u0010\u001b\u001a\u00020\u001cH&J\b\u0010\u001d\u001a\u00020\u001eH&\u00a8\u0006 "}, d2 = {"Lcom/porashona/studymaster/data/database/StudyDatabase;", "Landroidx/room/RoomDatabase;", "()V", "academicEventDao", "Lcom/porashona/studymaster/data/dao/AcademicEventDao;", "achievementDao", "Lcom/porashona/studymaster/data/dao/AchievementDao;", "blockedAppDao", "Lcom/porashona/studymaster/data/dao/BlockedAppDao;", "challengeDao", "Lcom/porashona/studymaster/data/dao/ChallengeDao;", "examDao", "Lcom/porashona/studymaster/data/dao/ExamDao;", "goalDao", "Lcom/porashona/studymaster/data/dao/GoalDao;", "noteDao", "Lcom/porashona/studymaster/data/dao/NoteDao;", "quoteDao", "Lcom/porashona/studymaster/data/dao/QuoteDao;", "routineDao", "Lcom/porashona/studymaster/data/dao/RoutineDao;", "studyResourceDao", "Lcom/porashona/studymaster/data/dao/StudyResourceDao;", "studySessionDao", "Lcom/porashona/studymaster/data/dao/StudySessionDao;", "subjectDao", "Lcom/porashona/studymaster/data/dao/SubjectDao;", "taskDao", "Lcom/porashona/studymaster/data/dao/TaskDao;", "userProfileDao", "Lcom/porashona/studymaster/data/dao/UserProfileDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.porashona.studymaster.data.model.StudySession.class, com.porashona.studymaster.data.model.Subject.class, com.porashona.studymaster.data.model.Routine.class, com.porashona.studymaster.data.model.Achievement.class, com.porashona.studymaster.data.model.UserProfile.class, com.porashona.studymaster.data.model.Goal.class, com.porashona.studymaster.data.model.Task.class, com.porashona.studymaster.data.model.Note.class, com.porashona.studymaster.data.model.Exam.class, com.porashona.studymaster.data.model.Challenge.class, com.porashona.studymaster.data.model.BlockedApp.class, com.porashona.studymaster.data.model.BlockStatistic.class, com.porashona.studymaster.data.model.Quote.class, com.porashona.studymaster.data.model.StudyResource.class, com.porashona.studymaster.data.model.AcademicEvent.class}, version = 2, exportSchema = false)
@androidx.room.TypeConverters(value = {com.porashona.studymaster.data.database.Converters.class})
public abstract class StudyDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.porashona.studymaster.data.database.StudyDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.data.database.StudyDatabase.Companion Companion = null;
    
    public StudyDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.StudySessionDao studySessionDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.SubjectDao subjectDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.RoutineDao routineDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.AchievementDao achievementDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.UserProfileDao userProfileDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.GoalDao goalDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.TaskDao taskDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.NoteDao noteDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.ExamDao examDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.ChallengeDao challengeDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.BlockedAppDao blockedAppDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.QuoteDao quoteDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.StudyResourceDao studyResourceDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.porashona.studymaster.data.dao.AcademicEventDao academicEventDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/porashona/studymaster/data/database/StudyDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/porashona/studymaster/data/database/StudyDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.data.database.StudyDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}