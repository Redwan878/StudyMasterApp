package com.porashona.studymaster.data.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b(\b\u0087\b\u0018\u00002\u00020\u0001B}\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0010H\u00c6\u0003J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\bH\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010.\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\u000eH\u00c6\u0003J\u008c\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\u000e2\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\u0010H\u00d6\u0001J\t\u00107\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0019R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001d\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u00068"}, d2 = {"Lcom/porashona/studymaster/data/model/StudyResource;", "", "id", "", "title", "", "url", "type", "Lcom/porashona/studymaster/data/model/ResourceType;", "subjectId", "subjectName", "description", "thumbnail", "isFavorite", "", "visitCount", "", "lastVisitedAt", "createdAt", "(JLjava/lang/String;Ljava/lang/String;Lcom/porashona/studymaster/data/model/ResourceType;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Long;J)V", "getCreatedAt", "()J", "getDescription", "()Ljava/lang/String;", "getId", "()Z", "getLastVisitedAt", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getSubjectId", "getSubjectName", "getThumbnail", "getTitle", "getType", "()Lcom/porashona/studymaster/data/model/ResourceType;", "getUrl", "getVisitCount", "()I", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;Ljava/lang/String;Lcom/porashona/studymaster/data/model/ResourceType;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/Long;J)Lcom/porashona/studymaster/data/model/StudyResource;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "study_resources")
public final class StudyResource {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String url = null;
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.model.ResourceType type = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long subjectId = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String subjectName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String thumbnail = null;
    private final boolean isFavorite = false;
    private final int visitCount = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long lastVisitedAt = null;
    private final long createdAt = 0L;
    
    public StudyResource(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String url, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.ResourceType type, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String thumbnail, boolean isFavorite, int visitCount, @org.jetbrains.annotations.Nullable
    java.lang.Long lastVisitedAt, long createdAt) {
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
    public final java.lang.String getUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.ResourceType getType() {
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
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getThumbnail() {
        return null;
    }
    
    public final boolean isFavorite() {
        return false;
    }
    
    public final int getVisitCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getLastVisitedAt() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long component1() {
        return 0L;
    }
    
    public final int component10() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component11() {
        return null;
    }
    
    public final long component12() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.ResourceType component4() {
        return null;
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
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.model.StudyResource copy(long id, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String url, @org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.ResourceType type, @org.jetbrains.annotations.Nullable
    java.lang.Long subjectId, @org.jetbrains.annotations.Nullable
    java.lang.String subjectName, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String thumbnail, boolean isFavorite, int visitCount, @org.jetbrains.annotations.Nullable
    java.lang.Long lastVisitedAt, long createdAt) {
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