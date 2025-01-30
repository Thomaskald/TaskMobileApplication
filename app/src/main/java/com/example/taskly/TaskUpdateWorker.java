package com.example.taskly;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.taskly.database.MyDatabase;
import com.example.taskly.database.MySingleton;
import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;

import java.time.LocalTime;
import java.util.List;

public class TaskUpdateWorker extends Worker {

    private final TaskDao taskDao;
    public TaskUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParameters){
        super(context, workerParameters);

        MyDatabase myDatabase = MySingleton.getInstance(context).getMyDatabase();
        taskDao = myDatabase.taskDao();
    }

    @NonNull
    @Override
    public Result doWork(){
        updateTaskStatuses();
        return Result.success();
    }

    private void updateTaskStatuses(){
        List<Task> tasks = taskDao.getAllTasks();

        for (Task task : tasks) {
            try{
                LocalTime taskStartTime =LocalTime.parse(task.getStartTime());
                int taskDuration = Integer.parseInt(task.getDuration());
                LocalTime endTime = taskStartTime.plusMinutes(taskDuration);
                LocalTime currentTime = LocalTime.now();

                if (task.getStatusId() != 4 && currentTime.isAfter(taskStartTime) && currentTime.isBefore(endTime)){
                    task.setStatusId(2);
                }else if (task.getStatusId() != 4 && currentTime.isAfter(endTime)){
                    task.setStatusId(3);
                }
                taskDao.updateTask(task);
            }catch(Exception e){

            }
        }
    }
}
