package com.porashona.studymaster.ui.goals;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0010\u0011B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u00020\u00062\n\u0010\t\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000bH\u0016R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/porashona/studymaster/ui/goals/GoalAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/porashona/studymaster/data/model/Goal;", "Lcom/porashona/studymaster/ui/goals/GoalAdapter$ViewHolder;", "onDelete", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "GoalDiffCallback", "ViewHolder", "app_debug"})
public final class GoalAdapter extends androidx.recyclerview.widget.ListAdapter<com.porashona.studymaster.data.model.Goal, com.porashona.studymaster.ui.goals.GoalAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.porashona.studymaster.data.model.Goal, kotlin.Unit> onDelete = null;
    
    public GoalAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.porashona.studymaster.data.model.Goal, kotlin.Unit> onDelete) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.porashona.studymaster.ui.goals.GoalAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.ui.goals.GoalAdapter.ViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/porashona/studymaster/ui/goals/GoalAdapter$GoalDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/porashona/studymaster/data/model/Goal;", "()V", "areContentsTheSame", "", "old", "new", "areItemsTheSame", "app_debug"})
    public static final class GoalDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.porashona.studymaster.data.model.Goal> {
        
        public GoalDiffCallback() {
            super();
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.Goal old, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.Goal p1_54480) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.Goal old, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.Goal p1_54480) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/porashona/studymaster/ui/goals/GoalAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/porashona/studymaster/databinding/ItemGoalBinding;", "(Lcom/porashona/studymaster/ui/goals/GoalAdapter;Lcom/porashona/studymaster/databinding/ItemGoalBinding;)V", "getBinding", "()Lcom/porashona/studymaster/databinding/ItemGoalBinding;", "bind", "", "goal", "Lcom/porashona/studymaster/data/model/Goal;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.porashona.studymaster.databinding.ItemGoalBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.databinding.ItemGoalBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.porashona.studymaster.databinding.ItemGoalBinding getBinding() {
            return null;
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.Goal goal) {
        }
    }
}