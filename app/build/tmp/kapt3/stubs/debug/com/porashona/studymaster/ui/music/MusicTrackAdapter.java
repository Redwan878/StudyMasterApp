package com.porashona.studymaster.ui.music;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0015\u0016B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\u000b\u001a\u00020\u00062\n\u0010\f\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\r\u001a\u00020\tH\u0016J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u0015\u0010\u0012\u001a\u00020\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\u0014R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\n\u00a8\u0006\u0017"}, d2 = {"Lcom/porashona/studymaster/ui/music/MusicTrackAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/porashona/studymaster/data/model/MusicTrack;", "Lcom/porashona/studymaster/ui/music/MusicTrackAdapter$TrackViewHolder;", "onTrackClick", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "selectedTrackId", "", "Ljava/lang/Integer;", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setSelectedTrack", "trackId", "(Ljava/lang/Integer;)V", "TrackDiffCallback", "TrackViewHolder", "app_debug"})
public final class MusicTrackAdapter extends androidx.recyclerview.widget.ListAdapter<com.porashona.studymaster.data.model.MusicTrack, com.porashona.studymaster.ui.music.MusicTrackAdapter.TrackViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.porashona.studymaster.data.model.MusicTrack, kotlin.Unit> onTrackClick = null;
    @org.jetbrains.annotations.Nullable
    private java.lang.Integer selectedTrackId;
    
    public MusicTrackAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.porashona.studymaster.data.model.MusicTrack, kotlin.Unit> onTrackClick) {
        super(null);
    }
    
    public final void setSelectedTrack(@org.jetbrains.annotations.Nullable
    java.lang.Integer trackId) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.porashona.studymaster.ui.music.MusicTrackAdapter.TrackViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.ui.music.MusicTrackAdapter.TrackViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/porashona/studymaster/ui/music/MusicTrackAdapter$TrackDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/porashona/studymaster/data/model/MusicTrack;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    public static final class TrackDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.porashona.studymaster.data.model.MusicTrack> {
        
        public TrackDiffCallback() {
            super();
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.MusicTrack oldItem, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.MusicTrack newItem) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.MusicTrack oldItem, @org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.MusicTrack newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/porashona/studymaster/ui/music/MusicTrackAdapter$TrackViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/porashona/studymaster/databinding/ItemMusicTrackBinding;", "(Lcom/porashona/studymaster/ui/music/MusicTrackAdapter;Lcom/porashona/studymaster/databinding/ItemMusicTrackBinding;)V", "bind", "", "track", "Lcom/porashona/studymaster/data/model/MusicTrack;", "app_debug"})
    public final class TrackViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.porashona.studymaster.databinding.ItemMusicTrackBinding binding = null;
        
        public TrackViewHolder(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.databinding.ItemMusicTrackBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.porashona.studymaster.data.model.MusicTrack track) {
        }
    }
}