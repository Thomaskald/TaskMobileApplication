package com.example.taskly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.taskly.database.MySingleton;
import com.example.taskly.database.TaskDao;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TaskUpdateWorker.class, 1, TimeUnit.HOURS).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("TaskUpdateWorker", ExistingPeriodicWorkPolicy.REPLACE, workRequest);
        TaskDao taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addNewTaskButton = findViewById(R.id.add_new_task_button);
        addNewTaskButton.setOnClickListener((v)->{
            // Go to add new task page
            Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
            startActivity(addTaskIntent);
        });

        Button viewTaskwsButton = findViewById(R.id.view_tasks_button);
        viewTaskwsButton.setOnClickListener((v)->{
            Intent viewTasksIntent = new Intent(this, ViewTasksActivity.class);
            startActivity(viewTasksIntent);
        });

        Button extractButton = findViewById(R.id.extract_tasks_button);
        extractButton.setOnClickListener((v)->{
            Exporter.exportTasks(this, taskDao);
            Toast.makeText(this, "Exporting tasks...", Toast.LENGTH_SHORT).show();
        });

        /*Button deleteTaskButton = findViewById(R.id.delete_task_button);
        deleteTaskButton.setOnClickListener((v)->{
            //Go to delete task page
            Intent deleteTaskIntent = new Intent(this, DeleteTaskActivity.class);
            startActivity(deleteTaskIntent);
        });*/

    }
}