package com.azubal.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.azubal.todoc.database.dao.ProjectDao;
import com.azubal.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {
    private final ProjectDao mProjectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        mProjectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects() {
        return mProjectDao.getProjects();
    }
}
