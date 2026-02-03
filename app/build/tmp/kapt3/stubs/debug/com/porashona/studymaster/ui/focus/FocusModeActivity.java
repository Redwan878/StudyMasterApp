package com.porashona.studymaster.ui.focus;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\u0010\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/porashona/studymaster/ui/focus/FocusModeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/porashona/studymaster/databinding/ActivityFocusModeBinding;", "isRunning", "", "timer", "Landroid/os/CountDownTimer;", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "startTimer", "duration", "", "app_debug"})
public final class FocusModeActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.porashona.studymaster.databinding.ActivityFocusModeBinding binding;
    @org.jetbrains.annotations.Nullable
    private android.os.CountDownTimer timer;
    private boolean isRunning = false;
    
    public FocusModeActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startTimer(long duration) {
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
}