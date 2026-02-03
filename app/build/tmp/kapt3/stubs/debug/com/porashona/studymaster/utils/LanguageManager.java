package com.porashona.studymaster.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tJ\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\tJ\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\tJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\tJ\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\tJ\u0016\u0010\u0016\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0004J\u000e\u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/porashona/studymaster/utils/LanguageManager;", "", "()V", "KEY_LANGUAGE", "", "LANGUAGE_BANGLA", "LANGUAGE_ENGLISH", "PREF_NAME", "applyLanguage", "Landroid/content/Context;", "context", "getLanguage", "getLanguageDisplayName", "languageCode", "isBangla", "", "isEnglish", "restartActivity", "", "activity", "Landroid/app/Activity;", "restartApp", "setLanguage", "wrapContext", "app_debug"})
public final class LanguageManager {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LANGUAGE_BANGLA = "bn";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String LANGUAGE_ENGLISH = "en";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREF_NAME = "language_pref";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_LANGUAGE = "selected_language";
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.utils.LanguageManager INSTANCE = null;
    
    private LanguageManager() {
        super();
    }
    
    /**
     * Get current language code
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLanguage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Set language and apply changes
     */
    public final void setLanguage(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String languageCode) {
    }
    
    /**
     * Apply saved language to context (for activities)
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Context applyLanguage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Wrap context with current language configuration
     */
    @org.jetbrains.annotations.NotNull
    public final android.content.Context wrapContext(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Check if current language is Bangla
     */
    public final boolean isBangla(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    /**
     * Check if current language is English
     */
    public final boolean isEnglish(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    /**
     * Get display name for language code
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLanguageDisplayName(@org.jetbrains.annotations.NotNull
    java.lang.String languageCode) {
        return null;
    }
    
    /**
     * Restart activity to apply language change
     */
    public final void restartActivity(@org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
    }
    
    /**
     * Restart app completely
     */
    public final void restartApp(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}