package com.example.taskly;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.CapabilityParams;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.taskly.database.Task;
import com.example.taskly.database.TaskDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Exporter {
    public static void exportTasks(Context context, TaskDao taskDao){
        new Thread(()->{
            List<Task> tasks = taskDao.getIncompleteTasks();

            StringBuilder content = new StringBuilder();
            content.append("Export\n");
            for( Task task : tasks){
                content.append("Id: ").append(task.getId()).append("\n");
                content.append("Name: ").append(task.getShortName()).append("\n");
                content.append("Description: ").append(task.getDescription()).append("\n");
                content.append("Start time: ").append(task.getStartTime()).append("\n");
                content.append("Duration: ").append(task.getDuration()).append("\n");
                content.append("Location: ").append(task.getLocation()).append("\n\n");
            }

            String fileName = "incomplete_tasks.txt";
            boolean success = saveToDownloads(context, fileName, content.toString());

            if(success){
                showToast(context, "Tasks exported successfully to Downloads folder.");
            }else{
                showToast(context, "Failed to export tasks.");
            }
        }).start();
    }

    private static boolean saveToDownloads(Context context, String fileName, String content){
        try{
            OutputStream outputStream;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                // Fro Android 10+
                ContentResolver resolver = context.getContentResolver();
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, "text/plain");
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                Uri fileUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if(fileUri == null){
                    return false;
                }
                outputStream = resolver.openOutputStream(fileUri);
            }else{
                // For Android 9 and below
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, fileName);
                outputStream = new FileOutputStream(file);
            }
            if(outputStream == null){
                return false;
            }
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    private static void showToast(Context context, String message){
        new android.os.Handler(context.getMainLooper()).post(()->Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }
}
