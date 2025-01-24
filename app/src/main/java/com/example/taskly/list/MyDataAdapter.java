package com.example.taskly.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskly.R;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataViewHolder> {
    private static final String TAG = "MyDataAdapter";
    private final List<MyData> list;
    public MyDataAdapter(List<MyData> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = inflater.inflate(R.layout.view_task_activity_element, parent, false);
        return new MyDataViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataViewHolder holder, int position){
        MyData data = list.get(position);
        holder.data_shortName_textView.setText(data.strShortName);
        holder.data_taskId_textView.setText("" + data.intTaskId);
        holder.data_description_textView.setText(data.strDescription);
        holder.data_startTime_textView.setText(data.strStartTime);
        holder.data_duration_textView.setText(data.strDuration);
        holder.data_location_textView.setText(data.strLocation);
        holder.view.setOnClickListener((v)->{
            Log.w(TAG, "List - Element Selected: " + holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }
    
    public void updateData(List<MyData> newData){
        list.clear();
        list.addAll(newData);
        notifyDataSetChanged();
    }
}
