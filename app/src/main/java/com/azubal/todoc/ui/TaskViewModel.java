package com.azubal.todoc.ui;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.azubal.todoc.model.Project;
import com.azubal.todoc.model.Task;
import com.azubal.todoc.repositories.ProjectDataRepository;
import com.azubal.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final ProjectDataRepository mProjectDataSource;
    private final TaskDataRepository mTaskDataSource;
    private final Executor mExecutor;

    @Nullable
    private LiveData<List<Project>> mProjects;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        mProjectDataSource = projectDataSource;
        mTaskDataSource = taskDataSource;
        mExecutor = executor;
    }

    public void init() {
        if (mProjects == null)
            mProjects = mProjectDataSource.getProjects();
    }

    @Nullable
    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    public LiveData<List<Task>> getTasks() {
        return mTaskDataSource.getTasks();
    }

    public void createTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.createTask(task));
    }

    public void deleteTask(Task task) {
        mExecutor.execute(() -> mTaskDataSource.deleteTask(task));
    }

    public void deleteAllTask() {mExecutor.execute(mTaskDataSource::deleteAllTask);}
}

