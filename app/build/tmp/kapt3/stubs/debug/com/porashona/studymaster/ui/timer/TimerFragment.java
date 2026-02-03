package com.porashona.studymaster.ui.timer;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J$\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u0017H\u0016J\b\u0010\"\u001a\u00020\u0017H\u0002J\u001a\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020\u001a2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010%\u001a\u00020\u0017H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\b\u0010\'\u001a\u00020\u0017H\u0002J\b\u0010(\u001a\u00020\u0017H\u0002J\u0010\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020+H\u0002J\u0010\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020.H\u0002J\b\u0010/\u001a\u00020\u0017H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011\u00a8\u00060"}, d2 = {"Lcom/porashona/studymaster/ui/timer/TimerFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentTimerBinding;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentTimerBinding;", "mediaPlayer", "Landroid/media/MediaPlayer;", "selectedSubject", "Lcom/porashona/studymaster/data/model/Subject;", "subjects", "", "viewModel", "Lcom/porashona/studymaster/ui/timer/TimerViewModel;", "getViewModel", "()Lcom/porashona/studymaster/ui/timer/TimerViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "getRepository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "loadSubjects", "", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onTimerFinished", "onViewCreated", "view", "playSound", "setupUI", "showAddSubjectDialog", "showSubjectPicker", "updateSessionTypeUI", "type", "Lcom/porashona/studymaster/data/model/SessionType;", "updateUIForState", "state", "Lcom/porashona/studymaster/ui/timer/TimerState;", "vibrate", "app_debug"})
public final class TimerFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentTimerBinding _binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.Nullable
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.porashona.studymaster.data.model.Subject> subjects;
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.data.model.Subject selectedSubject;
    
    public TimerFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentTimerBinding getBinding() {
        return null;
    }
    
    private final com.porashona.studymaster.ui.timer.TimerViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final com.porashona.studymaster.data.repository.StudyRepository getRepository() {
        return null;
    }
    
    private final void setupUI() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void loadSubjects() {
    }
    
    private final void updateUIForState(com.porashona.studymaster.ui.timer.TimerState state) {
    }
    
    private final void updateSessionTypeUI(com.porashona.studymaster.data.model.SessionType type) {
    }
    
    private final void showSubjectPicker() {
    }
    
    private final void showAddSubjectDialog() {
    }
    
    private final void onTimerFinished() {
    }
    
    private final void playSound() {
    }
    
    private final void vibrate() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}