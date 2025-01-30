package com.example.taskly;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskly.database.MySingleton;
import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;

public class TaskDetailsActivity extends AppCompatActivity {
    private TextView shortName, taskId, description, startTime, duration, location;
    private int taskIdValue;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        taskId = findViewById(R.id.task_id_details);
        shortName = findViewById(R.id.task_short_name_details);
        description = findViewById(R.id.task_description_details);
        startTime = findViewById(R.id.task_start_time_details);
        duration = findViewById(R.id.task_duration_details);
        location = findViewById(R.id.task_location_details);

        taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            taskIdValue = extras.getInt("taskId", -1);
            taskId.setText("Task Id: " + taskIdValue);
            shortName.setText(extras.getString("shortName"));
            description.setText(extras.getString("description"));
            startTime.setText(extras.getString("startTime"));
            duration.setText(extras.getString("duration"));
            location.setText(extras.getString("location"));
        }

        Button completeButton = findViewById(R.id.button_completed_details);
        completeButton.setOnClickListener((v)->{
            new Thread(()->{
                Task task = taskDao.getTaskById(taskIdValue);
                if(task != null){
                    task.setStatusId(4);
                    taskDao.updateTask(task);
                    runOnUiThread(()->{
                        Toast.makeText(this, "Task marked as completed", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }
            }).start();
        });

        Button deleteButton = findViewById(R.id.button_delete_details);
        deleteButton.setOnClickListener((v)->{

                new Thread(()->{
                    Task task = taskDao.getTaskById(taskIdValue);
                    if (task != null){
                        taskDao.deleteTask(task);
                        runOnUiThread(()->{
                            Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                }).start();
        });
    }
}
