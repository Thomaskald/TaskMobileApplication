package com.example.taskly;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.taskly.database.MyDatabase;
import com.example.taskly.database.MySingleton;
import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;
import com.example.taskly.list.MyData;
import com.example.taskly.list.MyDataAdapter;
import com.example.taskly.list.MyDataViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ViewTasksActivity extends AppCompatActivity {
    private TaskDao taskDao;
    private MyDataAdapter adapter;

    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_tasks_activity);

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TaskUpdateWorker.class, 1, TimeUnit.HOURS).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("TaskUpdateWorker", ExistingPeriodicWorkPolicy.REPLACE, workRequest);

        taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasks_recycler_view);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyDataAdapter(this, new ArrayList<>());
        tasksRecyclerView.setAdapter(adapter);

        loadTasks();


        // Print tasks in log
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

    private void loadTasks(){
        new Thread(()->{
            List<Task> tasks = taskDao.getAllTasks();

            List<MyData> myDataList = new ArrayList<>();

            for(Task task : tasks){
                if(task.getStatusId() != 4){
                    myDataList.add(new MyData(
                       task.getShortName(), task.getId(), task.getDescription(), task.getStartTime(), task.getDuration(), task.getLocation()
                    ));
                }
            }
             runOnUiThread(()->{
                 adapter.updateData(myDataList);
             });
        }).start();
    }
}
