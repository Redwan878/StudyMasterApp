package com.porashona.studymaster.ui.blocker;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0017B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u00020\t2\n\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001c\u0010\u0013\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/InstalledAppsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/porashona/studymaster/ui/blocker/InstalledAppsAdapter$ViewHolder;", "apps", "", "Lcom/porashona/studymaster/ui/blocker/BlockerFragment$AppInfo;", "onAppSelected", "Lkotlin/Function2;", "", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "selectedApps", "", "", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
public final class InstalledAppsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.porashona.studymaster.ui.blocker.InstalledAppsAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo> apps = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function2<com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo, java.lang.Boolean, kotlin.Unit> onAppSelected = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Set<java.lang.String> selectedApps = null;
    
    public InstalledAppsAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo> apps, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.porashona.studymaster.ui.blocker.BlockerFragment.AppInfo, ? super java.lang.Boolean, kotlin.Unit> onAppSelected) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.porashona.studymaster.ui.blocker.InstalledAppsAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.ui.blocker.InstalledAppsAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/porashona/studymaster/ui/blocker/InstalledAppsAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/porashona/studymaster/databinding/ItemBlockedAppBinding;", "(Lcom/porashona/studymaster/ui/blocker/InstalledAppsAdapter;Lcom/porashona/studymaster/databinding/ItemBlockedAppBinding;)V", "getBinding", "()Lcom/porashona/studymaster/databinding/ItemBlockedAppBinding;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.porashona.studymaster.databinding.ItemBlockedAppBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.databinding.ItemBlockedAppBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.databinding.ItemBlockedAppBinding getBinding() {
            return null;
        }
    }
}