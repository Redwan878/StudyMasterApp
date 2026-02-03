package com.porashona.studymaster.ui.blocker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0012\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\nH\u0014J\b\u0010\u0010\u001a\u00020\nH\u0002J\u0010\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/BlockOverlayActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/porashona/studymaster/databinding/ActivityBlockOverlayBinding;", "countDownTimer", "Landroid/os/CountDownTimer;", "getRandomMotivation", "", "goBackToStudy", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "setupUI", "startCountdown", "duration", "", "Companion", "app_debug"})
public final class BlockOverlayActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.porashona.studymaster.databinding.ActivityBlockOverlayBinding binding;
    @org.jetbrains.annotations.Nullable
    private android.os.CountDownTimer countDownTimer;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_PACKAGE_NAME = "package_name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_APP_NAME = "app_name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_TIME_REMAINING = "time_remaining";
    @org.jetbrains.annotations.NotNull
    public static final com.porashona.studymaster.ui.blocker.BlockOverlayActivity.Companion Companion = null;
    
    public BlockOverlayActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupUI() {
    }
    
    private final void startCountdown(long duration) {
    }
    
    private final void goBackToStudy() {
    }
    
    private final java.lang.String getRandomMotivation() {
        return null;
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/BlockOverlayActivity$Companion;", "", "()V", "EXTRA_APP_NAME", "", "EXTRA_PACKAGE_NAME", "EXTRA_TIME_REMAINING", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}