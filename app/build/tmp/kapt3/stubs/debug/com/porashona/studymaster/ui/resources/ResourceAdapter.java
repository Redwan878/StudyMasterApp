package com.porashona.studymaster.ui.resources;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002\u0010\u0011B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/porashona/studymaster/ui/resources/ResourceAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/porashona/studymaster/data/model/StudyResource;", "Lcom/porashona/studymaster/ui/resources/ResourceAdapter$VH;", "onClick", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "DiffCallback", "VH", "app_debug"})
public final class ResourceAdapter extends androidx.recyclerview.widget.ListAdapter<com.porashona.studymaster.data.model.StudyResource, com.porashona.studymaster.ui.resources.ResourceAdapter.VH> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.porashona.studymaster.data.model.StudyResource, kotlin.Unit> onClick = null;
    
    public ResourceAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.porashona.studymaster.data.model.StudyResource, kotlin.Unit> onClick) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.porashona.studymaster.ui.resources.ResourceAdapter.VH onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.ui.resources.ResourceAdapter.VH holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/porashona/studymaster/ui/resources/ResourceAdapter$DiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/porashona/studymaster/data/model/StudyResource;", "()V", "areContentsTheSame", "", "old", "new", "areItemsTheSame", "app_debug"})
    public static final class DiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.porashona.studymaster.data.model.StudyResource> {
        
        public DiffCallback() {
            super();
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.StudyResource old, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.StudyResource p1_54480) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.StudyResource old, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.StudyResource p1_54480) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/porashona/studymaster/ui/resources/ResourceAdapter$VH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/porashona/studymaster/databinding/ItemResourceBinding;", "(Lcom/porashona/studymaster/databinding/ItemResourceBinding;)V", "getBinding", "()Lcom/porashona/studymaster/databinding/ItemResourceBinding;", "app_debug"})
    public static final class VH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.porashona.studymaster.databinding.ItemResourceBinding binding = null;
        
        public VH(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.databinding.ItemResourceBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.databinding.ItemResourceBinding getBinding() {
            return null;
        }
    }
}