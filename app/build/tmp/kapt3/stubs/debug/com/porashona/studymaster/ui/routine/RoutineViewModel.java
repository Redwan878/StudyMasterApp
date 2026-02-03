package com.porashona.studymaster.ui.routine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bJ\u0016\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n\u00a8\u0006\u0018"}, d2 = {"Lcom/porashona/studymaster/ui/routine/RoutineViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "(Lcom/porashona/studymaster/data/repository/StudyRepository;)V", "routines", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/porashona/studymaster/data/model/Routine;", "getRoutines", "()Lkotlinx/coroutines/flow/StateFlow;", "subjects", "Lcom/porashona/studymaster/data/model/Subject;", "getSubjects", "addRoutine", "", "routine", "deleteRoutine", "toggleRoutine", "id", "", "enabled", "", "updateRoutine", "app_debug"})
public final class RoutineViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.repository.StudyRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Routine>> routines = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Subject>> subjects = null;
    
    public RoutineViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.repository.StudyRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Routine>> getRoutines() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Subject>> getSubjects() {
        return null;
    }
    
    public final void addRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine) {
    }
    
    public final void updateRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine) {
    }
    
    public final void deleteRoutine(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Routine routine) {
    }
    
    public final void toggleRoutine(long id, boolean enabled) {
    }
}