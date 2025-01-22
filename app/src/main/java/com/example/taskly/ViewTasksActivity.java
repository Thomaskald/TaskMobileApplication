package com.example.taskly;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskly.database.MyDatabase;
import com.example.taskly.database.MySingleton;
import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;

import java.util.List;

public class ViewTasksActivity extends AppCompatActivity {
    private TaskDao taskDao;

    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();

        new Thread(()->{
            List<Task> tasks = taskDao.getAllTasks();

            runOnUiThread(()->{
                for(Task task : tasks){
                    Log.d("TaskLog", "Task ID: " + task.getId() + ", Short name: " + task.getShortName() + ", Status: " + task.getStatusId());
                }
            });
        }).start();
    }
}
