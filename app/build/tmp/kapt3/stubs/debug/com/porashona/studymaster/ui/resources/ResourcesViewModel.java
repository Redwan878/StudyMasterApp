package com.porashona.studymaster.ui.resources;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, d2 = {"Lcom/porashona/studymaster/ui/resources/ResourcesViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/porashona/studymaster/data/repository/ExtendedRepository;", "(Lcom/porashona/studymaster/data/repository/ExtendedRepository;)V", "resources", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/porashona/studymaster/data/model/StudyResource;", "getResources", "()Lkotlinx/coroutines/flow/Flow;", "addResource", "Lkotlinx/coroutines/Job;", "res", "visitResource", "app_debug"})
public final class ResourcesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.repository.ExtendedRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> resources = null;
    
    public ResourcesViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.repository.ExtendedRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.StudyResource>> getResources() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job addResource(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource res) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job visitResource(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.StudyResource res) {
        return null;
    }
}