package com.example.taskly.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StatusDao {
    @Insert
    long insertStatus(Status status);

    @Query("SELECT * FROM statuses WHERE id = :id")
    Status getStatusById(int id);

    @Query("SELECT * FROM statuses")
    List<Status> getAllStatuses();

    @Update
    int updateStatus(Status status);
}
