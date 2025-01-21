package com.example.taskly.database;

import android.content.Context;

import androidx.room.Room;

public class MySingleton {
    private static MySingleton instance;
    private MyDatabase myDatabase;

    private MySingleton(Context context){
        myDatabase = Room.databaseBuilder(context, MyDatabase.class, "taskly_db").fallbackToDestructiveMigration().build();
    }

    public static MySingleton getInstance(Context context){
        if(instance == null){
            instance = new MySingleton(context);
        }
        return instance;
    }

    public MyDatabase getMyDatabase(){
        return myDatabase;
    }
}
