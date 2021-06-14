package com.azubal.todoc.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azubal.todoc.R;
import com.azubal.todoc.database.injections.Injection;
import com.azubal.todoc.database.injections.ViewModelFactory;
import com.azubal.todoc.model.Project;
import com.azubal.todoc.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {

    @NonNull
    private final ArrayList<Task> tasks = new ArrayList<>();
    private final TasksAdapter adapter = new TasksAdapter(tasks, this);
    public TaskViewModel mTaskViewModel;
    @Nullable
    public AlertDialog dialog = null;
    private Project[] allProjects = Project.getAllProjects();
    @Nullable
    private EditText dialogEditText = null;

    @Nullable
    private Spinner dialogSpinner = null;

    private RecyclerView listTasks;

    private TextView lblNoTasks;

    int idSort = 0 ;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listTasks.setAdapter(adapter);

        findViewById(R.id.fab_add_task).setOnClickListener(view -> showAddTaskDialog());

        configureViewModel();
        getProjects();


        switch(savedInstanceState != null ? savedInstanceState.getInt("idSort") : 0) {
            case 1:
                getTasksSortByAscTaskName();
                idSort = 1;
                // code block
                break;
            case 2:
                getTasksSortByDescTaskName();
                idSort = 2;
                // code block
                break;
            case 3:
                getTasksSortByAscNumberTime();
                idSort = 3;
                // code block
                break;
            case 4:
                getTasksSortByDescNumberTime();
                idSort = 4;
                // code block
                break;
            case 5:
                getTasksSortByAscProjectName();
                idSort = 5;
                // code block
                break;
            case 6:
                getTasksSortByDescProjectName();
                idSort = 6;
                // code block
                break;
            default:
                getTasks();
                // code block
        }

    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        mTaskViewModel = ViewModelProviders.of(this, viewModelFactory).get(TaskViewModel.class);
        mTaskViewModel.init();
    }

    private void getProjects() {
        mTaskViewModel.getProjects().observe(this, this::updateProjects);
    }

    private void updateProjects(List<Project> projects) {
        allProjects = projects.toArray(new Project[0]);
    }

    private void getTasks() {
        mTaskViewModel.getTasks().observe(this, this::updateTasks);
    }

    private void getTasksSortByDescProjectName() {
        mTaskViewModel.getTasksSortByDescProjectName().observe(this, this::updateTasks);
    }

    private void getTasksSortByAscProjectName() {
        mTaskViewModel.getTasksSortByAscProjectName().observe(this, this::updateTasks);
    }

    private void getTasksSortByAscNumberTime() {
        mTaskViewModel.getTasksSortByAscNumberTime().observe(this, this::updateTasks);
    }

    private void getTasksSortByDescNumberTime() {
        mTaskViewModel.getTasksSortByDescNumberTime().observe(this, this::updateTasks);
    }

    private void getTasksSortByAscTaskName() {
        mTaskViewModel.getTasksSortByAscTaskName().observe(this, this::updateTasks);
    }

    private void getTasksSortByDescTaskName() {
        mTaskViewModel.getTasksSortByDescTaskName().observe(this, this::updateTasks);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
                idSort = 1 ;
                getTasksSortByAscTaskName();
                return true;
            case R.id.filter_alphabetical_inverted:
                idSort = 2 ;
                getTasksSortByDescTaskName();
                return true;
            case R.id.filter_oldest_first:
                idSort = 3 ;
                getTasksSortByAscNumberTime();
                return true;
            case R.id.filter_recent_first:
                idSort = 4 ;
                getTasksSortByDescNumberTime();
                return true;

            case R.id.filter_alphabeticalProject:
                idSort = 5;
                getTasksSortByAscProjectName();

                return true;

            case R.id.filter_alphabetical_invertedProject:
                idSort = 6;
                getTasksSortByDescProjectName();

                return true;

            default:
                getTasks();
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("idSort", idSort);

        super.onSaveInstanceState(outState);
    }



    @Override
    public void onDeleteTask(Task task) {
        mTaskViewModel.deleteTask(task);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                Task task = new Task(
                        taskProject.getId(),
                        taskName,
                        new Date().getTime()
                );

                addTask(task);

                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    private void addTask(@NonNull Task task) {
        mTaskViewModel.createTask(task);
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks(List<Task> tasks) {
        if (tasks.size() == 0) {
            adapter.updateTasks(tasks);
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);

            adapter.updateTasks(tasks);
        }
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

}
