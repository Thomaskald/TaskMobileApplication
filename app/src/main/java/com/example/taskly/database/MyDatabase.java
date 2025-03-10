package com.example.taskly.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class, Status.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();
    public abstract StatusDao statusDao();
}
