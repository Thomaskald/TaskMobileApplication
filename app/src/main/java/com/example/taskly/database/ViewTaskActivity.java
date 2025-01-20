package com.example.taskly.database;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskly.R;

public class ViewTaskActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_tasks_activity);
    }
}
