package com.porashona.studymaster.ui.stats;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0015H\u0016J\u001a\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010!\u001a\u00020\u0015H\u0002J\"\u0010\"\u001a\u00020\u00152\u0018\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020&0%0$H\u0002J\"\u0010\'\u001a\u00020\u00152\u0018\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020&0%0$H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006("}, d2 = {"Lcom/porashona/studymaster/ui/stats/StatsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentStatsBinding;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentStatsBinding;", "viewModel", "Lcom/porashona/studymaster/ui/stats/StatsViewModel;", "getViewModel", "()Lcom/porashona/studymaster/ui/stats/StatsViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "formatTime", "", "seconds", "", "getRepository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupCharts", "updateBarChart", "data", "", "Lkotlin/Pair;", "", "updatePieChart", "app_debug"})
public final class StatsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentStatsBinding _binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    
    public StatsFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentStatsBinding getBinding() {
        return null;
    }
    
    private final com.porashona.studymaster.ui.stats.StatsViewModel getViewModel() {
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
    
    private final void setupCharts() {
    }
    
    private final void observeViewModel() {
    }
    
    private final java.lang.String formatTime(long seconds) {
        return null;
    }
    
    private final void updatePieChart(java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>> data) {
    }
    
    private final void updateBarChart(java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>> data) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}