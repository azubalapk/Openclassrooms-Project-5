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

    public void createTask(Task task) {
        mTaskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        mTaskDao.deleteTask(task);
    }

    public void deleteAllTask(){mTaskDao.deleteAllTask();}

    public LiveData<List<Task>> getTasks() {
        return mTaskDao.getTasks();
    }



    public LiveData<List<Task>>  getTasksSortByAscName(){ return mTaskDao.getTasksSortByAscName();}
    public LiveData<List<Task>>  getTasksSortByDescName(){ return mTaskDao.getTasksSortByDescName();}

    public LiveData<List<Task>>  getTasksSortByAscNumberTime(){return mTaskDao.getTasksSortByAscNumberTime();}
    public LiveData<List<Task>>  getTasksSortByDescNumberTime(){return mTaskDao.getTasksSortByDescNumberTime();}

    public LiveData<List<Task>>  getTasksSortByAscProjectName(){return mTaskDao.getTasksSortByAscProjectName();}
    public LiveData<List<Task>>  getTasksSortByDescProjectName(){return mTaskDao.getTasksSortByDescProjectName();}


}
