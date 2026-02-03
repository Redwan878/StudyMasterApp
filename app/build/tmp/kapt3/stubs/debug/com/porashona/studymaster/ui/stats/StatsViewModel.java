package com.porashona.studymaster.ui.stats;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R)\u0010\u000e\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\tR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\tR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\tR\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\tR)\u0010\u001d\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00100\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\t\u00a8\u0006\u001f"}, d2 = {"Lcom/porashona/studymaster/ui/stats/StatsViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/porashona/studymaster/data/repository/StudyRepository;", "(Lcom/porashona/studymaster/data/repository/StudyRepository;)V", "currentStreak", "Lkotlinx/coroutines/flow/StateFlow;", "", "getCurrentStreak", "()Lkotlinx/coroutines/flow/StateFlow;", "longestStreak", "getLongestStreak", "productivityScore", "getProductivityScore", "subjectTimeData", "", "Lkotlin/Pair;", "", "", "getSubjectTimeData", "todayTime", "", "getTodayTime", "totalSessions", "getTotalSessions", "totalTime", "getTotalTime", "weekTime", "getWeekTime", "weeklyData", "getWeeklyData", "app_debug"})
public final class StatsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.repository.StudyRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> todayTime = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> weekTime = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> totalTime = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> totalSessions = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> currentStreak = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> longestStreak = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>>> subjectTimeData = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>>> weeklyData = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> productivityScore = null;
    
    public StatsViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.repository.StudyRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getTodayTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getWeekTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getTotalTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotalSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getCurrentStreak() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getLongestStreak() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>>> getSubjectTimeData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<kotlin.Pair<java.lang.String, java.lang.Float>>> getWeeklyData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getProductivityScore() {
        return null;
    }
}