package com.azubal.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.azubal.todoc.model.Project;

import java.util.List;


@Dao
public interface ProjectDao {

    @Query("SELECT * FROM projects")
    LiveData<List<Project>> getProjects();

    @Insert
    void insertProjects(Project... projects);
}
