package com.example.taskly;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {
    private TextView shortName, taskId, description, startTime, duration, location;

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

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            taskId.setText(extras.getString("taskId"));
            shortName.setText(extras.getString("shortName"));
            description.setText(extras.getString("description"));
            startTime.setText(extras.getString("startTime"));
            duration.setText(extras.getString("duration"));
            location.setText(extras.getString("location"));
        }
    }
}
