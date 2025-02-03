package com.example.taskly;

import android.content.Intent;
import android.net.Uri;
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

                        // Return to main activity
                        Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
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

                            // Return to main activity
                            Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        });
                    }
                }).start();
        });

        Button locationButton = findViewById(R.id.button_location_details);
        locationButton.setOnClickListener((v)->{
            String locationText = location.getText().toString().trim();

            if(!locationText.isEmpty()){
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(locationText));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                //mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mapIntent);
                }else{
                    Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(locationText));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
                    startActivity(webIntent);
                }
            }else{
                Toast.makeText(this, "No location available fot this task", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
