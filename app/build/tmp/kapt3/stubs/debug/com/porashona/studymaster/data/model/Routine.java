package com.porashona.studymaster.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b#\b\u0087\b\u0018\u00002\u00020\u0001By\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0011H\u00c6\u0003J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0006H\u00c6\u0003J\t\u0010)\u001a\u00020\u0006H\u00c6\u0003J\t\u0010*\u001a\u00020\tH\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\tH\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u00c6\u0003J}\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00100\u001a\u00020\u00112\b\u00101\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00102\u001a\u00020\tH\u00d6\u0001J\t\u00103\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001aR\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\"\u00a8\u00064"}, d2 = {"Lcom/porashona/studymaster/data/model/Routine;", "", "id", "", "subjectId", "subjectName", "", "title", "hour", "", "minute", "durationMinutes", "repeatType", "Lcom/porashona/studymaster/data/model/RepeatType;", "repeatDays", "", "isEnabled", "", "createdAt", "(JJLjava/lang/String;Ljava/lang/String;IIILcom/porashona/studymaster/data/model/RepeatType;Ljava/util/List;ZJ)V", "getCreatedAt", "()J", "getDurationMinutes", "()I", "getHour", "getId", "()Z", "getMinute", "getRepeatDays", "()Ljava/util/List;", "getRepeatType", "()Lcom/porashona/studymaster/data/model/RepeatType;", "getSubjectId", "getSubjectName", "()Ljava/lang/String;", "getTitle", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "routines")
public final class Routine {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    private final long subjectId = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String subjectName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    private final int hour = 0;
    private final int minute = 0;
    private final int durationMinutes = 0;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.model.RepeatType repeatType = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.Integer> repeatDays = null;
    private final boolean isEnabled = false;
    private final long createdAt = 0L;
    
    public Routine(long id, long subjectId, @org.jetbrains.annotations.NotNull
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    java.lang.String title, int hour, int minute, int durationMinutes, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.RepeatType repeatType, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> repeatDays, boolean isEnabled, long createdAt) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    public final long getSubjectId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSubjectName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    public final int getHour() {
        return 0;
    }
    
    public final int getMinute() {
        return 0;
    }
    
    public final int getDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.RepeatType getRepeatType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Integer> getRepeatDays() {
        return null;
    }
    
    public final boolean isEnabled() {
        return false;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public Routine() {
        super();
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.RepeatType component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Integer> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.Routine copy(long id, long subjectId, @org.jetbrains.annotations.NotNull
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    java.lang.String title, int hour, int minute, int durationMinutes, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.RepeatType repeatType, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> repeatDays, boolean isEnabled, long createdAt) {
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