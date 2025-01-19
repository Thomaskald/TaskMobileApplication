package com.example.taskly.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statuses")
public class Status {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "status_name")
    private String statusName;

    public Status(String statusName){
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
