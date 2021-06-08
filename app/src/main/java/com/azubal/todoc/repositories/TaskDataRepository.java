package com.azubal.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.azubal.todoc.database.dao.TaskDao;
import com.azubal.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao mTaskDao;

    public TaskDataRepository(TaskDao taskDao) {
        mTaskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }

    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    public void deleteAllTask(){mTaskDao.deleteAllTask();}
}
