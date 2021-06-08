package com.azubal.todoc.database.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.azubal.todoc.database.LiveDataTestUtil;
import com.azubal.todoc.database.TodocDatabase;
import com.azubal.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private TodocDatabase database;
    private ProjectDao projectDao;
    private final Project[] projectsInModel = Project.getAllProjects();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        projectDao = database.projectDao();

    }

    @After
    public void closeDatabase() {
        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {

        projectDao.insertProjects(projectsInModel);

        List<Project> projects = LiveDataTestUtil.getValue(projectDao.getProjects());

        assertEquals(projects.get(0).getName(), projectsInModel[0].getName());
        assertEquals(projects.get(0).getId(), projectsInModel[0].getId());
        assertEquals(projects.get(0).getColor(), projectsInModel[0].getColor());

        assertEquals(projects.get(1).getName(), projectsInModel[1].getName());
        assertEquals(projects.get(1).getId(), projectsInModel[1].getId());
        assertEquals(projects.get(1).getColor(), projectsInModel[1].getColor());

        assertEquals(projects.get(2).getName(), projectsInModel[2].getName());
        assertEquals(projects.get(2).getId(), projectsInModel[2].getId());
        assertEquals(projects.get(2).getColor(), projectsInModel[2].getColor());
    }



}
