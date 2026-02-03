package com.porashona.studymaster;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0016R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0017"}, d2 = {"Lcom/porashona/studymaster/StudyMasterApplication;", "Landroid/app/Application;", "()V", "database", "Lcom/porashona/studymaster/data/database/StudyDatabase;", "getDatabase", "()Lcom/porashona/studymaster/data/database/StudyDatabase;", "database$delegate", "Lkotlin/Lazy;", "extendedRepository", "Lcom/porashona/studymaster/data/repository/ExtendedRepository;", "getExtendedRepository", "()Lcom/porashona/studymaster/data/repository/ExtendedRepository;", "extendedRepository$delegate", "preferencesManager", "Lcom/porashona/studymaster/data/preferences/PreferencesManager;", "getPreferencesManager", "()Lcom/porashona/studymaster/data/preferences/PreferencesManager;", "preferencesManager$delegate", "createNotificationChannels", "", "onCreate", "Companion", "app_debug"})
public final class StudyMasterApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy database$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy preferencesManager$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TIMER_CHANNEL_ID = "timer_channel";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ALERT_CHANNEL_ID = "alert_channel";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ROUTINE_CHANNEL_ID = "routine_channel";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String MUSIC_CHANNEL_ID = "music_channel";
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy extendedRepository$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.StudyMasterApplication.Companion Companion = null;
    
    public StudyMasterApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.database.StudyDatabase getDatabase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.preferences.PreferencesManager getPreferencesManager() {
        return null;
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void createNotificationChannels() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.data.repository.ExtendedRepository getExtendedRepository() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/porashona/studymaster/StudyMasterApplication$Companion;", "", "()V", "ALERT_CHANNEL_ID", "", "MUSIC_CHANNEL_ID", "ROUTINE_CHANNEL_ID", "TIMER_CHANNEL_ID", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}