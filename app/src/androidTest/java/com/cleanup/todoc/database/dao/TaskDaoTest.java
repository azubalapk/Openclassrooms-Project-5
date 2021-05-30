package com.cleanup.todoc.database.dao;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.database.LiveDataTestUtil;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TodocDatabase database;
    private ProjectDao projectDao;
    private TaskDao taskDao;
    private final Project[] projectsInModel = Project.getAllProjects();
    private final Task TASK_DEMO = new Task(1L,"aaa",130);

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        projectDao = database.projectDao();
        taskDao = database.taskDao();
        projectDao.insertProjects(projectsInModel);
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {

        taskDao.insertTask(TASK_DEMO);

        List<Task> tasks = LiveDataTestUtil.getValue(taskDao.getTasks());

        assertEquals(tasks.get(0).getName(), TASK_DEMO.getName());
        assertEquals(tasks.get(0).getProjectId(), TASK_DEMO.getProjectId());
        assertEquals(tasks.get(0).getCreationTimestamp(), TASK_DEMO.getCreationTimestamp());
    }

    @Test
    public void deleteAndGetTasks() throws InterruptedException {
        List<Task> tasks = LiveDataTestUtil.getValue(taskDao.getTasks());
        taskDao.insertTask(TASK_DEMO);
        taskDao.deleteTask(TASK_DEMO);
        assertTrue(tasks.isEmpty());
    }

}
