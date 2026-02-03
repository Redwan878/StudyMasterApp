package com.porashona.studymaster.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0011\u0010.\u001a\u00020/H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0019\u00101\u001a\u00020/2\u0006\u00102\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J\u0019\u00104\u001a\u00020/2\u0006\u00105\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J\u0012\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000f0\u000eJ\u0012\u00108\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000f0\u000eJ\u001b\u00109\u001a\u0004\u0018\u00010\u001a2\u0006\u0010:\u001a\u00020%H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010;J\u000e\u0010<\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u000eJ\u000e\u0010=\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u000eJ\u0011\u0010>\u001a\u00020/H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0011\u0010?\u001a\u00020/H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0019\u0010@\u001a\u00020%2\u0006\u00102\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J\u0019\u0010A\u001a\u00020%2\u0006\u0010B\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010CJ\u0019\u0010D\u001a\u00020%2\u0006\u00105\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106J!\u0010E\u001a\u00020/2\u0006\u0010:\u001a\u00020%2\u0006\u0010F\u001a\u00020GH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010HJ\u0019\u0010I\u001a\u00020/2\u0006\u0010J\u001a\u00020KH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010LJ\u0019\u0010M\u001a\u00020/2\u0006\u00102\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00103J\u0011\u0010N\u001a\u00020/H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00100J\u0019\u0010O\u001a\u00020/2\u0006\u00105\u001a\u00020\u001aH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00106R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u001d\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u001d\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0012R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0012R\u0019\u0010$\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010%0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0012R\u001d\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0012R\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\"0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u0012R\u0019\u0010+\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010,0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006P"}, d2 = {"Lcom/porashona/studymaster/data/repository/StudyRepository;", "", "sessionDao", "Lcom/porashona/studymaster/data/dao/StudySessionDao;", "subjectDao", "Lcom/porashona/studymaster/data/dao/SubjectDao;", "routineDao", "Lcom/porashona/studymaster/data/dao/RoutineDao;", "achievementDao", "Lcom/porashona/studymaster/data/dao/AchievementDao;", "profileDao", "Lcom/porashona/studymaster/data/dao/UserProfileDao;", "(Lcom/porashona/studymaster/data/dao/StudySessionDao;Lcom/porashona/studymaster/data/dao/SubjectDao;Lcom/porashona/studymaster/data/dao/RoutineDao;Lcom/porashona/studymaster/data/dao/AchievementDao;Lcom/porashona/studymaster/data/dao/UserProfileDao;)V", "allAchievements", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/porashona/studymaster/data/model/Achievement;", "getAllAchievements", "()Lkotlinx/coroutines/flow/Flow;", "allRoutines", "Lcom/porashona/studymaster/data/model/Routine;", "getAllRoutines", "allSessions", "Lcom/porashona/studymaster/data/model/StudySession;", "getAllSessions", "allSubjects", "Lcom/porashona/studymaster/data/model/Subject;", "getAllSubjects", "enabledRoutines", "getEnabledRoutines", "timeBySubject", "Lcom/porashona/studymaster/data/dao/SubjectTime;", "getTimeBySubject", "totalSessionCount", "", "getTotalSessionCount", "totalStudyTime", "", "getTotalStudyTime", "unlockedAchievements", "getUnlockedAchievements", "unlockedCount", "getUnlockedCount", "userProfile", "Lcom/porashona/studymaster/data/model/UserProfile;", "getUserProfile", "checkAchievements", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRoutine", "routine", "(Lcom/porashona/studymaster/data/model/Routine;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSubject", "subject", "(Lcom/porashona/studymaster/data/model/Subject;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSessionsForToday", "getSessionsForWeek", "getSubjectById", "id", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getTodayStudyTime", "getWeekStudyTime", "initializeAchievements", "initializeProfile", "insertRoutine", "insertSession", "session", "(Lcom/porashona/studymaster/data/model/StudySession;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSubject", "setRoutineEnabled", "enabled", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfileName", "name", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRoutine", "updateStreak", "updateSubject", "app_debug"})
public final class StudyRepository {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.StudySessionDao sessionDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.SubjectDao subjectDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.RoutineDao routineDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.AchievementDao achievementDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.dao.UserProfileDao profileDao = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> allSessions = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Long> totalStudyTime = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> totalSessionCount = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.dao.SubjectTime>> timeBySubject = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Subject>> allSubjects = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Routine>> allRoutines = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Routine>> enabledRoutines = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<com.porashona.studymaster.data.model.UserProfile> userProfile = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> allAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> unlockedAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> unlockedCount = null;
    
    public StudyRepository(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.StudySessionDao sessionDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.SubjectDao subjectDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.RoutineDao routineDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.AchievementDao achievementDao, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.dao.UserProfileDao profileDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getAllSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getTotalStudyTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getTotalSessionCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.dao.SubjectTime>> getTimeBySubject() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertSession(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudySession session, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getSessionsForToday() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudySession>> getSessionsForWeek() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getTodayStudyTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Long> getWeekStudyTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Subject>> getAllSubjects() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertSubject(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateSubject(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteSubject(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Subject subject, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getSubjectById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.porashona.studymaster.data.model.Subject> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Routine>> getAllRoutines() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Routine>> getEnabledRoutines() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object insertRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setRoutineEnabled(long id, boolean enabled, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.porashona.studymaster.data.model.UserProfile> getUserProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateProfileName(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateStreak(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> getAllAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Achievement>> getUnlockedAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getUnlockedCount() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeAchievements(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object checkAchievements(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}