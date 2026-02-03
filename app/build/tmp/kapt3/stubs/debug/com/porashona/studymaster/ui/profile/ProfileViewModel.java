package com.porashona.studymaster.ui.profile;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n\u00a8\u0006\u0014"}, d2 = {"Lcom/porashona/studymaster/ui/profile/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "(Lcom/porashona/studymaster/data/repository/StudyRepository;)V", "achievements", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/porashona/studymaster/data/model/Achievement;", "getAchievements", "()Lkotlinx/coroutines/flow/StateFlow;", "levelTitle", "", "getLevelTitle", "userProfile", "Lcom/porashona/studymaster/data/model/UserProfile;", "getUserProfile", "updateName", "", "name", "app_debug"})
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.repository.StudyRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.porashona.studymaster.data.model.UserProfile> userProfile = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Achievement>> achievements = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> levelTitle = null;
    
    public ProfileViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.repository.StudyRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.porashona.studymaster.data.model.UserProfile> getUserProfile() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.porashona.studymaster.data.model.Achievement>> getAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getLevelTitle() {
        return null;
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull
    java.lang.String name) {
    }
}