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
    void deleteTask(Task task);

    @Query("DELETE FROM tasks WHERE id = :id")
    int deleteTaskById(int id);

    @Query("SELECT * FROM tasks WHERE id = :id")
    Task getTaskById(int id);

}
