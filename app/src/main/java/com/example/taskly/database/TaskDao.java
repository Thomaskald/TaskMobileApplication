package com.example.taskly.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    public long storeTask(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);
}
