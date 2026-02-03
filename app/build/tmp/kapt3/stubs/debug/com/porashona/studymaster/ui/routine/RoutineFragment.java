package com.porashona.studymaster.ui.routine;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0016H\u0016J\u001a\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\"\u001a\u00020\u0016H\u0002J\b\u0010#\u001a\u00020\u0016H\u0002J\b\u0010$\u001a\u00020\u0016H\u0002J\u0010\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0002J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\'H\u0002J\u0012\u0010)\u001a\u00020\u00162\b\u0010*\u001a\u0004\u0018\u00010\'H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006+"}, d2 = {"Lcom/porashona/studymaster/ui/routine/RoutineFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentRoutineBinding;", "adapter", "Lcom/porashona/studymaster/ui/routine/RoutineAdapter;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentRoutineBinding;", "subjects", "", "Lcom/porashona/studymaster/data/model/Subject;", "viewModel", "Lcom/porashona/studymaster/ui/routine/RoutineViewModel;", "getViewModel", "()Lcom/porashona/studymaster/ui/routine/RoutineViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "getRepository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupFab", "setupRecyclerView", "showAddRoutineDialog", "showDeleteConfirmation", "routine", "Lcom/porashona/studymaster/data/model/Routine;", "showEditRoutineDialog", "showRoutineDialog", "existingRoutine", "app_debug"})
public final class RoutineFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentRoutineBinding _binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    private com.porashona.studymaster.ui.routine.RoutineAdapter adapter;
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.porashona.studymaster.data.model.Subject> subjects;
    
    public RoutineFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentRoutineBinding getBinding() {
        return null;
    }
    
    private final com.porashona.studymaster.ui.routine.RoutineViewModel getViewModel() {
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
    
    private final void setupRecyclerView() {
    }
    
    private final void setupFab() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void showAddRoutineDialog() {
    }
    
    private final void showEditRoutineDialog(com.porashona.studymaster.data.model.Routine routine) {
    }
    
    private final void showRoutineDialog(com.porashona.studymaster.data.model.Routine existingRoutine) {
    }
    
    private final void showDeleteConfirmation(com.porashona.studymaster.data.model.Routine routine) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}