package com.example.taskly;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.taskly.database.MyDatabase;
import com.example.taskly.database.MySingleton;
import com.example.taskly.database.Status;
import com.example.taskly.database.StatusDao;
import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {

    private Button addTaskButton;
    private EditText shortNameEditText, descriptionEditText, startTimeEditText, durationEditText, locationEditText;
    private TaskDao taskDao;
    private StatusDao statusDao;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        shortNameEditText = findViewById(R.id.shortName_view);
        descriptionEditText = findViewById(R.id.description_view);
        startTimeEditText = findViewById(R.id.startTime_view);
        durationEditText = findViewById(R.id.duration_view);
        locationEditText = findViewById(R.id.location_view);

        taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();
        statusDao = (StatusDao) MySingleton.getInstance(this).getMyDatabase().statusDao();

        addTaskButton = findViewById(R.id.addTask_button);
        addTaskButton.setOnClickListener((v)->{
            String shortName = shortNameEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String startTime = startTimeEditText.getText().toString().trim();
            String duration = durationEditText.getText().toString().trim();
            String location = locationEditText.getText().toString().trim();

            if(shortName.isEmpty() || description.isEmpty() || startTime.isEmpty() || duration.isEmpty()){
                Toast.makeText(this, "Please fill all the required fields.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!timeValidation(startTime)){
                Toast.makeText(this, "Invalid time format. Please use HH:mm.", Toast.LENGTH_SHORT).show();
                return;
            }
            new Thread(()->{
                Status recordedStatus = new Status("recorded");
                statusDao.insertStatus(recordedStatus);


                Task newTask = new Task(shortName, description, startTime, duration, 1, location);

                taskDao.storeTask(newTask);
                runOnUiThread(()->{Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();finish();});

                //printTasks();
            }).start();
        });
    }

    //@RequiresApi(api = Build.VERSION_CODES.O)
    private boolean timeValidation(String time) {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            sdf.setLenient(false);
            sdf.parse(time);
            return true;
        } catch (ParseException e){
            return false;
        }
    }

    /*private void printTasks(){
        new Thread(()->{
            List<Task> tasks = taskDao.getAllTasks();

            runOnUiThread(()->{
                for (Task task : tasks){
                    Log.d("TaskLog", "Task ID: " + task.getId() + ", Short name: " + task.getShortName() + ", Status: " + task.getStatusId());
                }
            });
        }).start();
    }*/
}
