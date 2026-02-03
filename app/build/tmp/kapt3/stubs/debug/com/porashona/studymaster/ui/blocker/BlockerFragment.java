package com.porashona.studymaster.ui.blocker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001:\u0001/B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\b\u0010\u0013\u001a\u00020\u0011H\u0002J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0002J$\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010\"\u001a\u00020\u0011H\u0016J\b\u0010#\u001a\u00020\u0011H\u0016J\u001a\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u001b2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\b\u0010&\u001a\u00020\u0011H\u0002J\b\u0010\'\u001a\u00020\u0011H\u0002J\b\u0010(\u001a\u00020\u0011H\u0002J\b\u0010)\u001a\u00020\u0011H\u0002J\b\u0010*\u001a\u00020\u0011H\u0002J\b\u0010+\u001a\u00020\u0011H\u0002J\b\u0010,\u001a\u00020\u0011H\u0002J\u0010\u0010-\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\u0018H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r\u00a8\u00060"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/BlockerFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentBlockerBinding;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentBlockerBinding;", "blockedAppsAdapter", "Lcom/porashona/studymaster/ui/blocker/BlockedAppsAdapter;", "viewModel", "Lcom/porashona/studymaster/ui/blocker/BlockerViewModel;", "getViewModel", "()Lcom/porashona/studymaster/ui/blocker/BlockerViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "checkAndRequestPermissions", "", "checkPermissions", "checkRootAccess", "getInstalledApps", "", "Lcom/porashona/studymaster/ui/blocker/BlockerFragment$AppInfo;", "isAccessibilityServiceEnabled", "", "observeViewModel", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onResume", "onViewCreated", "view", "openAccessibilitySettings", "setupRecyclerViews", "setupUI", "showAccessibilityDialog", "showAppSelectionDialog", "startBlocking", "stopBlocking", "updateBlockingUI", "enabled", "AppInfo", "app_debug"})
public final class BlockerFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentBlockerBinding _binding;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy viewModel$delegate = null;
    private com.porashona.studymaster.ui.blocker.BlockedAppsAdapter blockedAppsAdapter;
    
    public BlockerFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentBlockerBinding getBinding() {
        return null;
    }
    
    private final com.porashona.studymaster.ui.blocker.BlockerViewModel getViewModel() {
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
    
    private final void setupUI() {
    }
    
    private final void setupRecyclerViews() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateBlockingUI(boolean enabled) {
    }
    
    private final void checkPermissions() {
    }
    
    private final void checkAndRequestPermissions() {
    }
    
    private final boolean isAccessibilityServiceEnabled() {
        return false;
    }
    
    private final void showAccessibilityDialog() {
    }
    
    private final void openAccessibilitySettings() {
    }
    
    private final void checkRootAccess() {
    }
    
    private final void showAppSelectionDialog() {
    }
    
    private final java.util.List<com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo> getInstalledApps() {
        return null;
    }
    
    private final void startBlocking() {
    }
    
    private final void stopBlocking() {
    }
    
    @java.lang.Override
    public void onResume() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/BlockerFragment$AppInfo;", "", "packageName", "", "appName", "(Ljava/lang/String;Ljava/lang/String;)V", "getAppName", "()Ljava/lang/String;", "getPackageName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AppInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String packageName = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String appName = null;
        
        public AppInfo(@org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.lang.String appName) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPackageName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getAppName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String packageName, @org.jetbrains.annotations.NotNull
        java.lang.String appName) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}