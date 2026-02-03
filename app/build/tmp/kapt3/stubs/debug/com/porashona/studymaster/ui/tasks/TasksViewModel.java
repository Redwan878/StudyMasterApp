package com.porashona.studymaster.ui.tasks;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\bJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2 = {"Lcom/porashona/studymaster/ui/tasks/TasksViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/porashona/studymaster/data/repository/ExtendedRepository;", "(Lcom/porashona/studymaster/data/repository/ExtendedRepository;)V", "tasks", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/porashona/studymaster/data/model/Task;", "getTasks", "()Lkotlinx/coroutines/flow/Flow;", "addTask", "Lkotlinx/coroutines/Job;", "task", "deleteTask", "toggleTask", "isCompleted", "", "app_debug"})
public final class TasksViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.porashona.studymaster.data.repository.ExtendedRepository repository = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> tasks = null;
    
    public TasksViewModel(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.repository.ExtendedRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.porashona.studymaster.data.model.Task>> getTasks() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job addTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job deleteTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.Job toggleTask(@org.jetbrains.annotations.NotNull
    com.porashona.studymaster.data.model.Task task, boolean isCompleted) {
        return null;
    }
}