package com.porashona.studymaster.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/porashona/studymaster/utils/VoiceCommandManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getSpeechIntent", "Landroid/content/Intent;", "parseCommand", "Lcom/porashona/studymaster/utils/VoiceCommandManager$Command;", "text", "", "Command", "app_debug"})
public final class VoiceCommandManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    
    public VoiceCommandManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent getSpeechIntent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.porashona.studymaster.utils.VoiceCommandManager.Command parseCommand(@org.jetbrains.annotations.NotNull
    java.lang.String text) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/porashona/studymaster/utils/VoiceCommandManager$Command;", "", "(Ljava/lang/String;I)V", "START", "STOP", "PAUSE", "RESET", "MUSIC", "UNKNOWN", "app_debug"})
    public static enum Command {
        /*public static final*/ START /* = new START() */,
        /*public static final*/ STOP /* = new STOP() */,
        /*public static final*/ PAUSE /* = new PAUSE() */,
        /*public static final*/ RESET /* = new RESET() */,
        /*public static final*/ MUSIC /* = new MUSIC() */,
        /*public static final*/ UNKNOWN /* = new UNKNOWN() */;
        
        Command() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.porashona.studymaster.utils.VoiceCommandManager.Command> getEntries() {
            return null;
        }
    }
}