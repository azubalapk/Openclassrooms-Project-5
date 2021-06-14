package com.azubal.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.azubal.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTask();



    @Query("SELECT * FROM tasks ORDER BY name ASC")
    LiveData<List<Task>> getTasksSortByAscName();

    @Query("SELECT * FROM tasks ORDER BY name DESC")
    LiveData<List<Task>> getTasksSortByDescName();



    @Query("SELECT * FROM tasks ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksSortByAscNumberTime();

    @Query("SELECT * FROM tasks ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksSortByDescNumberTime();



    @Query(" SELECT * FROM projects INNER JOIN tasks ON projects.id = tasks.project_id "+" ORDER BY projects.name ASC")
    LiveData<List<Task>> getTasksSortByAscProjectName();

    @Query("SELECT *  FROM projects   INNER JOIN tasks  ON projects.id = tasks.project_id "+" ORDER BY projects.name DESC")
    LiveData<List<Task>> getTasksSortByDescProjectName();



}
