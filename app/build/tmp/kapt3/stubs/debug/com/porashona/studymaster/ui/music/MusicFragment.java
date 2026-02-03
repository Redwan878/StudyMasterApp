package com.porashona.studymaster.ui.music;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\b\u0010\u0015\u001a\u00020\u0013H\u0002J$\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!H\u0002J\u001a\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020\u00172\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010$\u001a\u00020\u0013H\u0002J\b\u0010%\u001a\u00020\u0013H\u0002J\u0012\u0010&\u001a\u00020\u00132\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0010\u0010\'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020\u000bH\u0002J\u0010\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\u000bH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/porashona/studymaster/ui/music/MusicFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/porashona/studymaster/databinding/FragmentMusicBinding;", "adapter", "Lcom/porashona/studymaster/ui/music/MusicTrackAdapter;", "binding", "getBinding", "()Lcom/porashona/studymaster/databinding/FragmentMusicBinding;", "isBound", "", "musicService", "Lcom/porashona/studymaster/service/MusicService;", "preferencesManager", "Lcom/porashona/studymaster/data/preferences/PreferencesManager;", "serviceConnection", "Landroid/content/ServiceConnection;", "bindMusicService", "", "observeMusicService", "observeSettings", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onTrackSelected", "track", "Lcom/porashona/studymaster/data/model/MusicTrack;", "onViewCreated", "view", "setupControls", "setupRecyclerView", "updateCurrentTrackUI", "updateMusicEnabledUI", "enabled", "updatePlayPauseButton", "isPlaying", "app_debug"})
public final class MusicFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.databinding.FragmentMusicBinding _binding;
    @org.jetbrains.annotations.Nullable
    private com.porashona.studymaster.service.MusicService musicService;
    private boolean isBound = false;
    private com.porashona.studymaster.ui.music.MusicTrackAdapter adapter;
    private com.porashona.studymaster.data.preferences.PreferencesManager preferencesManager;
    @org.jetbrains.annotations.NotNull
    private final android.content.ServiceConnection serviceConnection = null;
    
    public MusicFragment() {
        super();
    }
    
    private final com.porashona.studymaster.databinding.FragmentMusicBinding getBinding() {
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
    
    private final void setupRecyclerView() {
    }
    
    private final void setupControls() {
    }
    
    private final void observeSettings() {
    }
    
    private final void observeMusicService() {
    }
    
    private final void onTrackSelected(com.porashona.studymaster.data.model.MusicTrack track) {
    }
    
    private final void updatePlayPauseButton(boolean isPlaying) {
    }
    
    private final void updateCurrentTrackUI(com.porashona.studymaster.data.model.MusicTrack track) {
    }
    
    private final void updateMusicEnabledUI(boolean enabled) {
    }
    
    private final void bindMusicService() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}