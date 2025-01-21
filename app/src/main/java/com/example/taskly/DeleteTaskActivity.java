package com.example.taskly;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskly.database.MySingleton;
import com.example.taskly.database.TaskDao;

public class DeleteTaskActivity extends AppCompatActivity {
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_task_activity);

        EditText taskId = findViewById(R.id.deleteId_view);
        Button deleteTaskIdButton = findViewById(R.id.delete_task_by_id_button);

        taskDao = MySingleton.getInstance(this).getMyDatabase().taskDao();

        deleteTaskIdButton.setOnClickListener((v)->{
            String taskIdString = taskId.getText().toString().trim();

            if(taskIdString.isEmpty()){
                Toast.makeText(this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                return;
            }

            int taskToBeDeleted = Integer.parseInt(taskIdString);

            new Thread(()->{
                int rowsAffected = taskDao.deleteTaskById(taskToBeDeleted);

                runOnUiThread(()->{
                    if(rowsAffected > 0){
                        Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Provided ID not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

    }
}
