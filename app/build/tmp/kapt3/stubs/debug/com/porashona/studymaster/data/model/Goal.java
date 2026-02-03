package com.porashona.studymaster.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b*\b\u0087\b\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\u0013J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u000b\u0010.\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\fH\u00c6\u0003J\t\u00100\u001a\u00020\u000eH\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\u008c\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u0007H\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\u000e2\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\u0007H\u00d6\u0001J\t\u00107\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0011\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010 R\u0011\u0010\u0012\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001aR\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\"\u0010\u0015R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001cR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001c\u00a8\u00068"}, d2 = {"Lcom/porashona/studymaster/data/model/Goal;", "", "id", "", "title", "", "targetMinutes", "", "currentMinutes", "subjectId", "subjectName", "goalType", "Lcom/porashona/studymaster/data/model/GoalType;", "isCompleted", "", "createdAt", "completedAt", "date", "streakCount", "(JLjava/lang/String;IILjava/lang/Long;Ljava/lang/String;Lcom/porashona/studymaster/data/model/GoalType;ZJLjava/lang/Long;Ljava/lang/String;I)V", "getCompletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCreatedAt", "()J", "getCurrentMinutes", "()I", "getDate", "()Ljava/lang/String;", "getGoalType", "()Lcom/porashona/studymaster/data/model/GoalType;", "getId", "()Z", "getStreakCount", "getSubjectId", "getSubjectName", "getTargetMinutes", "getTitle", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;IILjava/lang/Long;Ljava/lang/String;Lcom/porashona/studymaster/data/model/GoalType;ZJLjava/lang/Long;Ljava/lang/String;I)Lcom/porashona/studymaster/data/model/Goal;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "goals")
public final class Goal {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    private final int targetMinutes = 0;
    private final int currentMinutes = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long subjectId = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String subjectName = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.model.GoalType goalType = null;
    private final boolean isCompleted = false;
    private final long createdAt = 0L;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long completedAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String date = null;
    private final int streakCount = 0;
    
    public Goal(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, int targetMinutes, int currentMinutes, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.GoalType goalType, boolean isCompleted, long createdAt, @org.jetbrains.annotations.Nullable
    java.lang.Long completedAt, @org.jetbrains.annotations.NotNull
    java.lang.String date, int streakCount) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    public final int getTargetMinutes() {
        return 0;
    }
    
    public final int getCurrentMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getSubjectId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSubjectName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.GoalType getGoalType() {
        return null;
    }
    
    public final boolean isCompleted() {
        return false;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getCompletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDate() {
        return null;
    }
    
    public final int getStreakCount() {
        return 0;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component11() {
        return null;
    }
    
    public final int component12() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.GoalType component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final long component9() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.Goal copy(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, int targetMinutes, int currentMinutes, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.GoalType goalType, boolean isCompleted, long createdAt, @org.jetbrains.annotations.Nullable
    java.lang.Long completedAt, @org.jetbrains.annotations.NotNull
    java.lang.String date, int streakCount) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}