package com.example.taskly.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "tasks", foreignKeys = {
                @ForeignKey(
                        entity = Status.class,
                        parentColumns = {"id"},
                        childColumns = {"status_id"}
                )
        }
)
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "short_name")
    private String shortName;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "start_time")
    private String startTime; // Time in HH:mm format

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "status_id")
    private int statusId;

    @ColumnInfo(name = "location")
    private String location;

    public Task(String shortName, String description, String startTime, String duration, int statusId, String location){
        this.shortName = shortName;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
        this.statusId = statusId;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

/*    @Override
    public String ToString(){
        return "Task{" +
                "id=" + id +
                ", shortName=" + shortName +'\'' +
                ", description=" + description +'\'' +
                ", startTime=" + startTime + '\'' +
                ", duration=" + duration + '\'' +
                ", statusId=" + statusId + '\'' +
                ", location=" + location + '\'' +
                '}';
    }*/
}
