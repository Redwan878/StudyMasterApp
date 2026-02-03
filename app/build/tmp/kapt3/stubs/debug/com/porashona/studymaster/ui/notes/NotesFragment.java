package com.porashona.studymaster.ui.notes;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u001a\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0014\u0010!\u001a\u00020\u001f2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0012\u001a\u0010\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/porashona/studymaster/ui/notes/NotesFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentNotesBinding;", "activeDialogBinding", "Lcom/porashona/studymaster/databinding/DialogAddNoteBinding;", "adapter", "Lcom/porashona/studymaster/ui/notes/NoteAdapter;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentNotesBinding;", "viewModel", "Lcom/porashona/studymaster/ui/notes/NotesViewModel;", "getViewModel", "()Lcom/porashona/studymaster/ui/notes/NotesViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "voiceLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onViewCreated", "", "view", "showAddNoteDialog", "existingNote", "Lcom/porashona/studymaster/data/model/Note;", "app_debug"})
public final class NotesFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentNotesBinding _binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    private com.porashona.studymaster.ui.notes.NoteAdapter adapter;
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.DialogAddNoteBinding activeDialogBinding;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> voiceLauncher = null;
    
    public NotesFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentNotesBinding getBinding() {
        return null;
    }
    
    private final com.porashona.studymaster.ui.notes.NotesViewModel getViewModel() {
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
    
    private final void showAddNoteDialog(com.porashona.studymaster.data.model.Note existingNote) {
    }
}