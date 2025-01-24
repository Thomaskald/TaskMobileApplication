package com.example.taskly.list;

public class MyData {

    public String strShortName;

    public Integer intTaskId;

    public String strDescription;

    public String strStartTime;

    public String strDuration;

    public String strLocation;

    public MyData(String strShortName, Integer intTaskId, String strDescription, String strStartTime, String strDuration, String strLocation){
        this.strShortName = strShortName;
        this.intTaskId = intTaskId;
        this.strDescription = strDescription;
        this.strStartTime = strStartTime;
        this.strDuration = strDuration;
        this.strLocation = strLocation;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "strShortName='" + strShortName + '\'' +
                ", intTaskId=" + intTaskId +
                ", strDescription='" + strDescription + '\'' +
                ", strStartTime='" + strStartTime + '\'' +
                ", strDuration='" + strDuration + '\'' +
                ", strLocation='" + strLocation + '\'' +
                '}';
    }
}
