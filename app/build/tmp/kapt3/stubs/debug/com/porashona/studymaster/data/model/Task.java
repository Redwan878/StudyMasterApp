package com.porashona.studymaster.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b4\b\u0087\b\u0018\u00002\u00020\u0001B\u00bb\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0019J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u000eH\u00c6\u0003J\t\u00103\u001a\u00020\u0011H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u00105\u001a\u00020\u0014H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u00108\u001a\u00020\u000eH\u00c6\u0003J\u0010\u00109\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010<\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010=\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010>\u001a\u00020\nH\u00c6\u0003J\u0010\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010@\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010A\u001a\u00020\u000eH\u00c6\u0003J\u00c6\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u000e2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010CJ\u0013\u0010D\u001a\u00020\u000e2\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020\u0014H\u00d6\u0001J\t\u0010G\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b!\u0010\u001bR\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001eR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010$R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010$R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b%\u0010\u001bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0017\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b+\u0010\u001bR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b,\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100\u00a8\u0006H"}, d2 = {"Lcom/porashona/studymaster/data/model/Task;", "", "id", "", "title", "", "description", "subjectId", "subjectName", "priority", "Lcom/porashona/studymaster/data/model/TaskPriority;", "dueDate", "dueTime", "isCompleted", "", "isRecurring", "recurringType", "Lcom/porashona/studymaster/data/model/RecurringType;", "parentTaskId", "xpReward", "", "createdAt", "completedAt", "reminderEnabled", "reminderTime", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Lcom/porashona/studymaster/data/model/TaskPriority;Ljava/lang/Long;Ljava/lang/String;ZZLcom/porashona/studymaster/data/model/RecurringType;Ljava/lang/Long;IJLjava/lang/Long;ZLjava/lang/Long;)V", "getCompletedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCreatedAt", "()J", "getDescription", "()Ljava/lang/String;", "getDueDate", "getDueTime", "getId", "()Z", "getParentTaskId", "getPriority", "()Lcom/porashona/studymaster/data/model/TaskPriority;", "getRecurringType", "()Lcom/porashona/studymaster/data/model/RecurringType;", "getReminderEnabled", "getReminderTime", "getSubjectId", "getSubjectName", "getTitle", "getXpReward", "()I", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Lcom/porashona/studymaster/data/model/TaskPriority;Ljava/lang/Long;Ljava/lang/String;ZZLcom/porashona/studymaster/data/model/RecurringType;Ljava/lang/Long;IJLjava/lang/Long;ZLjava/lang/Long;)Lcom/porashona/studymaster/data/model/Task;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "tasks")
public final class Task {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long subjectId = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String subjectName = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.model.TaskPriority priority = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long dueDate = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String dueTime = null;
    private final boolean isCompleted = false;
    private final boolean isRecurring = false;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.model.RecurringType recurringType = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long parentTaskId = null;
    private final int xpReward = 0;
    private final long createdAt = 0L;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long completedAt = null;
    private final boolean reminderEnabled = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long reminderTime = null;
    
    public Task(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.TaskPriority priority, @org.jetbrains.annotations.Nullable
    java.lang.Long dueDate, @org.jetbrains.annotations.Nullable
    java.lang.String dueTime, boolean isCompleted, boolean isRecurring, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.RecurringType recurringType, @org.jetbrains.annotations.Nullable
    java.lang.Long parentTaskId, int xpReward, long createdAt, @org.jetbrains.annotations.Nullable
    java.lang.Long completedAt, boolean reminderEnabled, @org.jetbrains.annotations.Nullable
    java.lang.Long reminderTime) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescription() {
        return null;
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
    public final com.porashona.studymaster.data.model.TaskPriority getPriority() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getDueDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDueTime() {
        return null;
    }
    
    public final boolean isCompleted() {
        return false;
    }
    
    public final boolean isRecurring() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.RecurringType getRecurringType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getParentTaskId() {
        return null;
    }
    
    public final int getXpReward() {
        return 0;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getCompletedAt() {
        return null;
    }
    
    public final boolean getReminderEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getReminderTime() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.RecurringType component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component12() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final long component14() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component15() {
        return null;
    }
    
    public final boolean component16() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.TaskPriority component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.Task copy(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.TaskPriority priority, @org.jetbrains.annotations.Nullable
    java.lang.Long dueDate, @org.jetbrains.annotations.Nullable
    java.lang.String dueTime, boolean isCompleted, boolean isRecurring, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.RecurringType recurringType, @org.jetbrains.annotations.Nullable
    java.lang.Long parentTaskId, int xpReward, long createdAt, @org.jetbrains.annotations.Nullable
    java.lang.Long completedAt, boolean reminderEnabled, @org.jetbrains.annotations.Nullable
    java.lang.Long reminderTime) {
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